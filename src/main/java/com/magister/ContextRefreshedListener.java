package com.magister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.magister.db.DatabasePopulator;
import com.magister.network.service.SensorNetworkService;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DatabasePopulator populator;

    @Autowired
    private SensorNetworkService service;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        populator.populateDatabase();
        service.emulateNetworks();
    }
}
