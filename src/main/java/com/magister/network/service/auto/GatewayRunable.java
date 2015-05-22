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

public class GatewayRunable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayRunable.class);

    private Mote mote;

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public GatewayRunable(Mote mote) {
        this.mote = mote;
    }

    @Transactional
    @Override
    public void run() {
        LOG.info("{} awakened", mote);
        if (!Thread.currentThread().isInterrupted()) {
            try {
                mote = entityManager.merge(mote);
                Random random = new Random();
                // 1) save sensor data
                // 2) assume gateway connected to downstream
                // and don't lose power

                SensorData sensorData = new SensorData();
                sensorData.setTimestamp(new Timestamp(System.currentTimeMillis()));
                sensorData.setValue(String.valueOf(random.nextInt(100)));

                mote.setPower(mote.getPower() - 1);
                mote.getMetering().add(sensorData);
                moteRepository.save(mote);
            } catch (Throwable t) {
                LOG.error("Exception during sensor wake up", t);
            }
        }
    }

}
