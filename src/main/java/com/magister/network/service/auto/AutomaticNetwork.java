package com.magister.network.service.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.repository.MoteRepository;
import com.magister.db.repository.NetworkRepository;
import com.magister.db.repository.SensorDataRepository;

public class AutomaticNetwork implements Callable<Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger(AutomaticNetwork.class);

    private ScheduledExecutorService timer = Executors.newScheduledThreadPool(4);

    private final Network network;
    private final Random random;

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private NodeRunnableFactory nodeRunnableFactory;

    public AutomaticNetwork(Network network) {
        this.network = network;
        this.random = new Random();
    }

    @Override
    public Boolean call() throws Exception {
        try {
            return callInternal();
        } finally {
            timer.shutdown();
        }
    }

    /**
     * @return true, if nobody left alive in network, false otherwise
     */
    @Transactional
    public Boolean callInternal() throws Exception {
        // try to find any gateway node
        Mote gateway = null;

        List<Mote> motes = network.getMotes();

        if (motes.size() == 0) {
            LOG.warn("Network {} has no motes and will not be emulated", network);
            return true;
        }

        for (Mote mote : motes) {
            if (mote.isGateway()) {
                gateway = mote;
                break;
            }
        }

        // if we didn't find one, pick random
        if (gateway == null) {
            gateway = motes.get(random.nextInt(motes.size()));
        }

        // 1) set other nodes as not gateway anymore
        for (Mote mote : motes) {
            if (mote != gateway) {
                mote.setGateway(false);
            }
        }

        if (Thread.currentThread().isInterrupted()) {
            LOG.info("Network {} emulation was interrupted", network);
            return false;
        }

        // preliminary save updated configuration
        networkRepository.save(network);

        // there is a posibility that
        List<ScheduledFuture<?>> nonGatewayFutures = new ArrayList<>();

        // 2) other nodes report to gateway
        // 3) schedule their execution according to
        // their delays
        for (Mote mote : motes) {
            if (mote.isAlive() && (mote != gateway)) {
                long delay = mote.getDelay();
                Runnable command = nodeRunnableFactory.createGatewayRunnable(mote);
                ScheduledFuture<?> schedule = timer.scheduleAtFixedRate(command, delay, delay, TimeUnit.SECONDS);
                nonGatewayFutures.add(schedule);
            }
        }

        // keep waiting for all non gateway schedules to complete
        // or until interrupt
        try {
            Runnable command = nodeRunnableFactory.createGatewayRunnable(gateway);
            long delay = gateway.getDelay();
            timer.scheduleAtFixedRate(command, delay, gateway.getDelay(), TimeUnit.SECONDS);
            for (ScheduledFuture<?> scheduledFuture : nonGatewayFutures) {
                scheduledFuture.get();
            }
            return true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        return false;
    }

}
