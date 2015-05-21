package com.magister.network.service;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magister.db.domain.Network;
import com.magister.db.repository.NetworkRepository;

@Service
public class SensorNetworkService {

    private static final Logger LOG = LoggerFactory.getLogger(SensorNetworkService.class);

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Map<Long, Future<Boolean>> networks = new ConcurrentHashMap<>();

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkCallableFactory factory;

    public void reconfigure(Network network) {
        Future<Boolean> future = networks.get(network.getId());
        future.cancel(true);

        long networkId = network.getId();
        Network reloadedNetwork = networkRepository.findOne(networkId);
        emulateNetwork(reloadedNetwork);
    }

    public void emulateNetworks() {
        LOG.info("Loading sensor networks definitions from database");
        // when context reloaded and application is started
        // we should start all networks
        Iterable<Network> networks = networkRepository.findAll();
        for (Network network : networks) {
            emulateNetwork(network);
        }
    }

    private void emulateNetwork(Network network) {
        Callable<Boolean> networkCallable = factory.createNetworkCallable(network);
        Future<Boolean> future = executor.submit(networkCallable);
        networks.put(network.getId(), future);
    }
}
