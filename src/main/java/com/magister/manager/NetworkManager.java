package com.magister.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;
import com.magister.db.domain.Network;
import com.magister.db.domain.Topology;
import com.magister.db.repository.MoteRepository;
import com.magister.db.repository.NetworkRepository;
import com.magister.network.service.NetworkEmulationService;

@Service
public class NetworkManager {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private NetworkEmulationService networkEmulationService;

    public static Mote findLiveGateway(Network network) {
        final List<Mote> motes = network.getMotes();
        for (Mote mote : motes) {
            if (mote.isGateway() && mote.isAlive()) {
                return mote;
            }
        }

        return null;
    }

    @Transactional
    public void saveOrUpdateNetwork(Network network) {
        Assert.hasText(network.getName(), "Network must has a name");

        // check that at least one gateway is present
        Mote gateway = null;
        List<Mote> motes = network.getMotes();
        for (Mote mote : motes) {
            if (mote.isGateway()) {
                gateway = mote;
                break;
            }
        }
        Assert.notNull(gateway, "Network must has at least ONE gateway");

        // TODO: maybe assert that it is working?
        // and working is assumed for manual only when all sensors are reachable?
        networkEmulationService.reorganizeTopology(network);

        // when we save new network we need to start/restart it's emulation
        networkRepository.save(network);
        networkEmulationService.emulateNetwork(network, false);
    }

    @Transactional
    public void removeNetwork(long id) {
        Network network = networkRepository.findOne(id);
        network.getTopology().getLinks().clear();
        networkEmulationService.cancelEmulation(network);
        networkRepository.delete(network);
    }

    @Transactional
    public void updateNetwork(Network network) {
        //reread network and update some fields
        Network network2 = networkRepository.findOne(network.getId());
        network2.setEnabled(network.isEnabled());
        network2.setName(network.getName());
        network2.setStatus(network.getStatus());
        network2.setMode(network.getMode());

        List<Mote> motes = network.getMotes();
        Map<Long, Mote> motesMap = new HashMap<>();
        for (Mote mote : motes) {
            Mote mote2 = moteRepository.findOne(mote.getId());
            mote2.setGateway(mote.isGateway());
            motesMap.put(mote2.getId(), mote2);
        }
        network2.setMotes(new ArrayList<>(motesMap.values()));

        Topology topology = network.getTopology();
        List<MoteLink> links = topology.getLinks();
        for (MoteLink moteLink : links) {
            moteLink.setSource(motesMap.get(moteLink.getSource().getId()));
            moteLink.setTarget(motesMap.get(moteLink.getTarget().getId()));
        }
        network2.getTopology().setLinks(links);

        saveOrUpdateNetwork(network2);
    }
}
