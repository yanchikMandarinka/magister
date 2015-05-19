package com.magister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magister.sensors.SensorNode;
import com.magister.sensors.WirelessSensorNetwork;

@Controller
@RequestMapping("/network")
public class SensorNetworkController {

    @Autowired
    private WirelessSensorNetwork sensorNetwork;

    @RequestMapping("/sensors")
    @ResponseBody
    public List<SensorNode> listSensors() {
        return sensorNetwork.getSensorNodes();
    }

}