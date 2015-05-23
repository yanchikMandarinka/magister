package com.magister.network.service.auto;

import java.sql.Timestamp;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.magister.db.domain.Mote;
import com.magister.db.domain.SensorData;
import com.magister.db.repository.MoteRepository;
import com.magister.db.repository.SensorDataRepository;

public class MoteRunable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(MoteRunable.class);

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Mote mote;

    public MoteRunable(Mote mote) {
        this.mote = mote;
    }

    @Transactional
    @Override
    public void run() {
        LOG.info("{} awakened", mote);
        try {
            mote = entityManager.find(Mote.class, mote.getId());
            if (!Thread.currentThread().isInterrupted() && mote.isAlive()) {
                Random random = new Random();

                SensorData sensorData = new SensorData();
                sensorData.setTimestamp(new Timestamp(System.currentTimeMillis()));
                sensorData.setValue(String.valueOf(random.nextInt(100)));

                if (!mote.isGateway()) {
                    // assume gateway doesn't lose power
                    mote.setPower(mote.getPower() - 1);
                }

                mote.getMetering().add(sensorData);

                // update mote state in database
                moteRepository.save(mote);
            } else {
                LOG.info("{} interrupted", mote);
            }
        } catch (Throwable t) {
            LOG.error("Exception during sensor wake up", t);
        }
    }
}
