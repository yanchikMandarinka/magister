package com.magister.network.service;

import java.util.concurrent.Callable;

import com.magister.db.domain.Network;

public interface NetworkCallableFactory {
    Callable<Boolean> createNetworkCallable(Network network);
}
