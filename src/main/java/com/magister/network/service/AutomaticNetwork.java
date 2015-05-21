package com.magister.network.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;

public class AutomaticNetwork implements Callable<Boolean> {

    private final Network network;
    private final Random random;

    private Mote gateway;

    public AutomaticNetwork(Network network) {
        this.network = network;
        this.random = new Random();
    }

    /**
     * @return true, if nobody left alive in network, false otherwise
     */
    @Override
    public Boolean call() throws Exception {
        // find random mote to be a gateway
        List<Mote> motes = network.getMotes();
        List<Mote> liveMotes = new ArrayList<>();
        for (Mote mote : motes) {
            if (mote.isAlive()) {
                liveMotes.add(mote);
            }
        }

        if (liveMotes.size() == 0) {
            return true;
        }

        gateway = liveMotes.remove(random.nextInt(motes.size()));

        // other nodes report to gateway


        return null;
    }

}
