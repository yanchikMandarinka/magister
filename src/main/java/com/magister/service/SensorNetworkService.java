package com.magister.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.magister.db.domain.Network;
import com.magister.db.repository.NetworkRepository;

@Service
public class SensorNetworkService implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(SensorNetworkService.class);

    @Autowired
    private NetworkRepository networkRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        LOG.info("Loading sensor networks definitions from database");
        // when context reloaded and application is started
        // we should start all networks
        Iterable<Network> networks = networkRepository.findAll();
    }

}
