package com.magister.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;
import com.magister.db.domain.Network;
import com.magister.db.domain.Network.Mode;
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

    /**
     * Determines if network topology forms connected graph, i.e. all alive sensors are reachable from gateways.
     *
     * @param network
     * @return
     */
    public static boolean isConnected(Network network) {
        List<Mote> motes = network.getMotes();
        Topology topology = network.getTopology();
        List<MoteLink> links = topology.getLinks();

        // building adjacency list
        Map<Long, List<Long>> graph = new HashMap<>();
        List<Long> gateways = new ArrayList<>();
        int liveMotes = 0;
        for (Mote mote : motes) {
            if (mote.isAlive()) {
                ++liveMotes;
                long moteId = mote.getId();
                if (mote.isGateway()) {
                    gateways.add(moteId);
                }
                graph.put(moteId, new ArrayList<Long>());
            }
        }
        for (MoteLink moteLink : links) {
            Mote source = moteLink.getSource();
            Mote target = moteLink.getTarget();

            // links between dead nodes considered as broken
            if (source.isAlive() && target.isAlive()) {
                // nodes are transfering data from source to target
                // but we need to check that they are reachable from reverse way
                // so we do the trick: swap target and source here
                graph.get(target.getId()).add(source.getId());
            }
        }

        // traversing graph with bfs from all gateways all the way down
        Map<Long, Boolean> visitedMap = new HashMap<>();
        Queue<Long> queue = new LinkedList<>();
        for (Long gateway : gateways) {
            visitedMap.put(gateway, true);
            queue.add(gateway);
        }
        while (!queue.isEmpty()) {
            Long current = queue.poll();
            List<Long> adjacents = graph.get(current);
            for (Long adjacent : adjacents) {
                if (!visitedMap.containsKey(adjacent)) {
                    visitedMap.put(adjacent, true);
                    queue.add(adjacent);
                }
            }
        }

        return (liveMotes == visitedMap.size());
    }

    @Transactional
    public void saveOrUpdateNetwork(Network network) {
        Assert.hasText(network.getName(), "Network must has a name");

        // check that at least one gateway is present
        Mote gateway = findLiveGateway(network);
        Assert.notNull(gateway, "Network must has at least ONE gateway");

        if (network.getMode() == Mode.MANUAL) {
            Assert.isTrue(isConnected(network), "All live sensor nodes should be reachable from gateways");
        }

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
        // reread network and update some fields
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
