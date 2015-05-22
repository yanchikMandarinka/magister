package com.magister.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.domain.Mote.MoteType;
import com.magister.db.domain.Network.Mode;
import com.magister.db.domain.SensorData;
import com.magister.db.repository.NetworkRepository;

@Component
public class DatabasePopulator {

    @Autowired
    private NetworkRepository networkRepository;

    @Transactional
    public void populateDatabase() {
        Network network = new Network();
        network.setName("Laboratory 1");
        network.setMode(Mode.AUTOMATIC);

        List<Mote> motes = new ArrayList<>();
        motes.add(createMote(9999, 10.0, 10.0, MoteType.TEMPERATURE, false));
        motes.add(createMote(9999, 11.0, 10.0, MoteType.LIGHTNESS, false));
        motes.add(createMote(9999, 12.0, 10.0, MoteType.TEMPERATURE, false));
        motes.add(createMote(9999, 13.0, 10.0, MoteType.TEMPERATURE, false));
        motes.add(createMote(9999, 10.0, 11.0, MoteType.TEMPERATURE, true));
        motes.add(createMote(9999, 11.0, 12.0, MoteType.LIGHTNESS, false));
        motes.add(createMote(9999, 12.0, 13.0, MoteType.TEMPERATURE, false));
        motes.add(createMote(9999, 13.0, 10.0, MoteType.TEMPERATURE, false));

        network.setMotes(motes);
        networkRepository.save(network);

        Network network2 = new Network();
        network2.setName("Laboratory 2");
        network2.setMode(Mode.AUTOMATIC);

        List<Mote> motes2 = new ArrayList<>();
        motes2.add(createMote(5555, 25.0, 28.0, MoteType.TEMPERATURE, false));
        motes2.add(createMote(5555, 26.0, 27.0, MoteType.LIGHTNESS, true));
        motes2.add(createMote(5555, 27.0, 26.0, MoteType.LIGHTNESS, false));

        network2.setMotes(motes2);
        networkRepository.save(network2);
    }

    private Mote createMote(int power, double latitude, double longtitude, MoteType moteType, boolean gateway) {
        Random random = new Random();

        Mote mote = new Mote();
        mote.setPower(random.nextInt(9999));
        mote.setLatitude(-90 + random.nextInt(180));
        mote.setLongtitude(-180 + random.nextInt(360));
        mote.setMoteType(moteType);
        mote.setGateway(gateway);
        mote.setDelay(10 + random.nextInt(100));

        mote.setMetering(createRandomSensorData(mote));

        return mote;
    }

    private List<SensorData> createRandomSensorData(Mote mote) {
        Random random = new Random();
        List<SensorData> data = new ArrayList<>();

        long current = System.currentTimeMillis();
        int points = random.nextInt(100);
        long shiftBack = points * mote.getDelay() * 1000;
        for (int i = 0; i < points; ++i) {
            SensorData sensorData = new SensorData();
            sensorData.setTimestamp(new Timestamp(current - shiftBack + i * mote.getDelay() * 1000));
            sensorData.setValue(String.valueOf(random.nextInt(100)));
            data.add(sensorData);
        }
        return data;
    }

}
