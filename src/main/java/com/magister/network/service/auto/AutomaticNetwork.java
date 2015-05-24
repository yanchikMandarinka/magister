package com.magister.network.service.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.repository.MoteRepository;
import com.magister.db.repository.NetworkRepository;
import com.magister.manager.AutomaticNetworkManager;

public class AutomaticNetwork implements Callable<Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger(AutomaticNetwork.class);

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private MoteRunnableFactory moteRunnableFactory;

    @Autowired
    private AutomaticNetworkManager manager;

    private final Network network;
    private final ScheduledExecutorService timer;

    public AutomaticNetwork(Network network) {
        this.network = network;
        this.timer = Executors.newScheduledThreadPool(4);
    }

    @Override
    public Boolean call() throws Exception {
        try {
            manager.prepareNetwork(network);
            return callInternal();
        } catch (Exception e) {
            LOG.error("Error during network emulation {}", network, e);
            throw e;
        } finally {
            timer.shutdown();
        }
    }

    /**
     * @return true, if nobody left alive in network, false otherwise
     */
    public Boolean callInternal() throws Exception {
        final List<Mote> motes = network.getMotes();

        if (Thread.currentThread().isInterrupted()) {
            LOG.info("Network {} emulation was interrupted", network);
            return false;
        }

        List<ScheduledFuture<?>> futures = new ArrayList<>();

        for (Mote mote : motes) {
            if (mote.isAlive()) {
                long delay = mote.getDelay();
                Runnable command = moteRunnableFactory.createMoteRunnable(mote);
                ScheduledFuture<?> schedule = timer.scheduleAtFixedRate(command, delay, delay, TimeUnit.SECONDS);
                futures.add(schedule);
            }
        }

        // keep waiting for all non gateway schedules to complete
        // or until interrupt
        try {
            for (ScheduledFuture<?> scheduledFuture : futures) {
                scheduledFuture.get();
            }
            return true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        return false;
    }

}
