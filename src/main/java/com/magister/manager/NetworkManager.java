package com.magister.manager;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.repository.NetworkRepository;
import com.magister.network.service.NetworkEmulationService;

@Service
public class NetworkManager {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkEmulationService networkEmulationService;

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

        // when we save new network we need to start/restart it's emulation
        networkRepository.save(network);
        networkEmulationService.emulateNetwork(network);
    }

    @Transactional
    public void removeNetwork(long id) {
        Network network = networkRepository.findOne(id);

        networkEmulationService.cancelEmulation(network);
        networkRepository.delete(network);
    }
}
