package com.magister.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteType;
import com.magister.db.domain.Network;
import com.magister.db.domain.Network.Mode;
import com.magister.db.domain.SensorData;
import com.magister.manager.NetworkManager;

@Component
public class DatabasePopulator {
    private static final Logger LOG = LoggerFactory.getLogger(DatabasePopulator.class);

    @Autowired
    private NetworkManager networkManager;

    @Transactional
    public void populateDatabase() {
        LOG.info("Creating data for fake sensor networks");

        Random random = new Random();

        Network network = new Network();
        network.setName("Laboratory 1");
        network.setMode(Mode.AUTOMATIC);

        List<Mote> motes = new ArrayList<>();
        motes.add(createMote(MoteType.TEMPERATURE, false));
        motes.add(createMote(MoteType.LIGHTNESS, false));
        motes.add(createMote(MoteType.TEMPERATURE, false));
        motes.add(createMote(MoteType.TEMPERATURE, true));
        motes.add(createMote(MoteType.LIGHTNESS, false));
        motes.add(createMote(MoteType.TEMPERATURE, false));
        motes.add(createMote(MoteType.TEMPERATURE, false));

        // network was running for N minutes
        long runtime = 5 + random.nextInt(10);// from 5 to 15 minutes
        long current = System.currentTimeMillis();
        for (Mote mote : motes) {
            mote.setMetering(createRandomSensorData(mote, runtime, current));
        }

        network.setMotes(motes);
        networkManager.saveOrUpdateNetwork(network);

        Network network2 = new Network();
        network2.setName("Laboratory 2");
        network2.setMode(Mode.AUTOMATIC);

        List<Mote> motes2 = new ArrayList<>();
        motes2.add(createMote(MoteType.TEMPERATURE, false));
        motes2.add(createMote(MoteType.LIGHTNESS, true));
        motes2.add(createMote(MoteType.LIGHTNESS, false));

        runtime = 5 + random.nextInt(10);// from 5 to 15 minutes
        current = System.currentTimeMillis();
        for (Mote mote : motes2) {
            mote.setMetering(createRandomSensorData(mote, runtime, current));
        }

        network2.setMotes(motes2);
        networkManager.saveOrUpdateNetwork(network2);
    }

    private Mote createMote(MoteType moteType, boolean gateway) {
        Random random = new Random();

        Mote mote = new Mote();
        mote.setPower(random.nextInt(500));
        mote.setLatitude(-90 + random.nextInt(180));
        mote.setLongtitude(-180 + random.nextInt(360));
        mote.setMoteType(moteType);
        mote.setGateway(gateway);
        mote.setDelay(5 + random.nextInt(30));

        return mote;
    }

    private Set<SensorData> createRandomSensorData(Mote mote, long runtime, long current) {
        Random random = new Random();
        Set<SensorData> data = new HashSet<>();
        long points = (runtime * 60) / mote.getDelay();
        for (int i = 0; i < points; ++i) {
            SensorData sensorData = new SensorData();
            long runtimeInMs = runtime * 60 * 1000;
            sensorData.setTimestamp(new Timestamp(current - runtimeInMs + i * mote.getDelay() * 1000));
            sensorData.setValue(String.valueOf(random.nextInt(100)));
            data.add(sensorData);
        }
        return data;
    }

}
