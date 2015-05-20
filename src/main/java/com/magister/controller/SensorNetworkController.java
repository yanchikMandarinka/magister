package com.magister.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magister.db.domain.Network;
import com.magister.db.repository.NetworkRepository;
import com.magister.sensors.SensorNode;
import com.magister.sensors.WirelessSensorNetwork;

@Controller
@RequestMapping("/network")
public class SensorNetworkController {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private WirelessSensorNetwork sensorNetwork;

    @RequestMapping("/sensors")
    @ResponseBody
    public List<SensorNode> listSensors() {
        return sensorNetwork.getSensorNodes();
    }

    @RequestMapping("/create")
    @ResponseBody
    public Network createNetwork() {
        Network network = new Network();
        network.setName(String.valueOf(new Random().nextDouble()));
        return networkRepository.save(network);
    }

}
