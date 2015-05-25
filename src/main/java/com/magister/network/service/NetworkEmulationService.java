package com.magister.network.service;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magister.db.domain.Network;
import com.magister.db.domain.Network.Mode;
import com.magister.db.repository.NetworkRepository;
import com.magister.manager.AutomaticNetworkManager;
import com.magister.manager.ManualNetworkManager;

@Service
public class NetworkEmulationService {

    private static final Logger LOG = LoggerFactory.getLogger(NetworkEmulationService.class);

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Map<Long, Future<Boolean>> runningNetworks = new ConcurrentHashMap<>();

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkCallableFactory factory;

    @Autowired
    private AutomaticNetworkManager automaticNetworkManager;

    @Autowired
    private ManualNetworkManager manualNetworkManager;

    @Transactional
    public void emulateNetwork(Network network) {
        cancelEmulation(network);

        //TODO: assuming that we call #emulateNetwork after #saveNetwork
        // we will save network twice: first in saveNetwork and second after update
        reorganizeTopology(network);

        emulateNetworkInternal(network);
    }

    public void cancelEmulation(Network network) {
        Future<Boolean> future = runningNetworks.get(network.getId());
        // cancel emulation if it is already running
        if (future != null) {
            LOG.info("{} emulation was cancelled", network);
            future.cancel(true);
        }
    }

    private void emulateNetworkInternal(Network network) {
        LOG.info("Starting network emulation for {}", network);
        Callable<Boolean> networkCallable = factory.createNetworkCallable(network);
        Future<Boolean> future = executor.submit(networkCallable);
        runningNetworks.put(network.getId(), future);
    }

    @Transactional
    public void reorganizeTopology(Network network) {
        if (network.getMode() == Mode.AUTOMATIC) {
            automaticNetworkManager.reorganizeTopology(network);
        } else {
            manualNetworkManager.reorganizeTopology(network);
        }
    }
}
