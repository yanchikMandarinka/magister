package com.magister.network.service.manual;

import java.sql.Timestamp;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;
import com.magister.db.domain.Network;
import com.magister.db.domain.SensorData;
import com.magister.db.repository.MoteLinkRepository;
import com.magister.db.repository.MoteRepository;
import com.magister.db.repository.SensorDataRepository;

public class ManualMoteRunable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ManualMoteRunable.class);

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private MoteLinkRepository moteLinkRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final Network network;
    private final Mote mote;

    public ManualMoteRunable(Mote mote, Network network) {
        this.network = network;
        this.mote = mote;
    }

    @Transactional
    @Override
    public void run() {
        LOG.info("{} awakened", mote);
        if (Thread.currentThread().isInterrupted()) {
            LOG.info("{} interrupted", mote);
            return;
        }

        try {
            Mote freshMote = entityManager.find(Mote.class, mote.getId());
            // we should send data according to topology
            if (mote.isAlive()) {

                Random random = new Random();

                SensorData sensorData = new SensorData();
                sensorData.setTimestamp(new Timestamp(System.currentTimeMillis()));
                sensorData.setValue(String.valueOf(random.nextInt(100)));


                // assume gateway doesn't lose power
                // and doesn't send data anywhere
                if (!freshMote.isGateway()) {
                    freshMote.setPower(freshMote.getPower() - 1);

                    MoteLink link = moteLinkRepository.findBySourceId(freshMote.getId());
                    Mote sourceMote = link.getTarget();
                    LOG.info("{} transferring data to {}", freshMote, sourceMote);
                    if (!sourceMote.isGateway()) {
                        sourceMote.setPower(sourceMote.getPower() - 1);
                        moteRepository.save(sourceMote);
                    }
                }

                freshMote.getMetering().add(sensorData);

                // update mote state in database
                moteRepository.save(freshMote);
            }
        } catch (Throwable t) {
            LOG.error("Exception during sensor wake up", t);
        }
    }
}
