package com.magister.network.service.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.domain.Network.Status;
import com.magister.network.service.MoteRunnableFactory;

public class AutomaticNetwork implements Callable<Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger(AutomaticNetwork.class);

    @Autowired
    @Qualifier("moteRunnableFactory")
    private MoteRunnableFactory moteRunnableFactory;

    private final Network network;
    private final ScheduledExecutorService timer;

    public AutomaticNetwork(Network network) {
        this.network = network;
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(4);
        scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        this.timer = scheduledThreadPoolExecutor;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            return callInternal();
        } catch (Exception e) {
            LOG.error("Error during network emulation {}", network, e);
            throw e;
        } finally {
            timer.shutdown();
        }
    }

    /**
     * @return true, if network couldn't be emulated, false otherwise
     */
    public Boolean callInternal() throws Exception {
        if (!network.isEnabled()) {
            LOG.warn("{} is disabled and will not be emulated", network);
            return true;
        }

        if (network.getStatus() == Status.NO_LIVE_MOTES) {
            LOG.warn("Network {} has no motes and will not be emulated", network);
            return true;
        }

        if (network.getStatus() == Status.NO_LIVE_GATEWAYS) {
            LOG.warn("Network {} has no live gateway and will not be emulated", network);
            return true;
        }

        if (Thread.currentThread().isInterrupted()) {
            LOG.info("Network {} emulation was interrupted", network);
            return false;
        }

        List<Mote> motes = network.getMotes();
        List<ScheduledFuture<?>> futures = new ArrayList<>();

        for (Mote mote : motes) {
            if (mote.isAlive()) {
                long delay = mote.getDelay();
                Runnable command = moteRunnableFactory.createMoteRunnable(mote, network);
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
        } finally {
            // cancel all schedulled tasks
            for (ScheduledFuture<?> scheduledFuture : futures) {
                scheduledFuture.cancel(true);
            }
        }

        return false;
    }

}
