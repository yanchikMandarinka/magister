package com.magister.network.service;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

import com.magister.db.domain.Network;

@Component
public class NetworkCallableFactory {

    public Callable<Boolean> createNetworkCallable(Network network) {
        switch(network.getMode()) {
            case AUTOMATIC: {
                return new AutomaticNetwork(network);
            }
            case MANUAL: {
                // what to do here?)
                return new AutomaticNetwork(network);
            }
            default: {
                throw new UnsupportedOperationException("Unknown sensor network mode");
            }
        }
    }
}
