package com.magister.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.repository.NetworkRepository;

@Component
public class DatabasePopulator implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private NetworkRepository networkRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        populateDatabase();
    }

    private void populateDatabase() {
        Network network = new Network();
        network.setName("Laboratory 1");

        List<Mote> motes = new ArrayList<>();
        motes.add(createMote(9999, 10.0, 10.0));
        motes.add(createMote(9999, 11.0, 10.0));
        motes.add(createMote(9999, 12.0, 10.0));
        motes.add(createMote(9999, 13.0, 10.0));
        motes.add(createMote(9999, 10.0, 11.0));
        motes.add(createMote(9999, 11.0, 12.0));
        motes.add(createMote(9999, 12.0, 13.0));
        motes.add(createMote(9999, 13.0, 10.0));

        network.setMotes(motes);
        networkRepository.save(network);

        Network network2 = new Network();
        network2.setName("Laboratory 2");

        List<Mote> motes2 = new ArrayList<>();
        motes2.add(createMote(5555, 25.0, 28.0));
        motes2.add(createMote(5555, 26.0, 27.0));
        motes2.add(createMote(5555, 27.0, 26.0));
        motes2.add(createMote(5555, 28.0, 25.0));

        network2.setMotes(motes2);
        networkRepository.save(network2);
    }

    private Mote createMote(int power, double latitude, double longtitude) {
        Mote mote = new Mote();
        mote.setPower(power);
        mote.setLatitude(latitude);
        mote.setLongtitude(longtitude);
        return mote;
    }

}
