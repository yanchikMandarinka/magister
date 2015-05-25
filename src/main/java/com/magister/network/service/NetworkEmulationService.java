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
import com.magister.db.repository.NetworkRepository;

@Service
public class NetworkEmulationService {

    private static final Logger LOG = LoggerFactory.getLogger(NetworkEmulationService.class);

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Map<Long, Future<Boolean>> runningNetworks = new ConcurrentHashMap<>();

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkCallableFactory factory;

    @Transactional
    public void emulateNetwork(Network network) {
        cancelEmulation(network);

        long networkId = network.getId();
        Network reloadedNetwork = networkRepository.findOne(networkId);
        emulateNetworkInternal(reloadedNetwork);
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
}
