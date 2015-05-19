package com.magister;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.magister.sensors.SensorNetworkGateway;
import com.magister.sensors.SensorNode;
import com.magister.sensors.WirelessSensorNetwork;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WirelessSensorNetwork sensorNetwork() {
        WirelessSensorNetwork sensorNetwork = new WirelessSensorNetwork(gateway(), sensorNodes());
        return sensorNetwork;
    }

    @Bean
    public SensorNetworkGateway gateway() {
        return new SensorNetworkGateway();
    }

    public List<SensorNode> sensorNodes() {
        List<SensorNode> nodes = new ArrayList<>();

        // TODO: configure different nodes
        for (int i = 0; i < 100; ++i) {
            SensorNode sensorNode = new SensorNode(i, gateway());
            nodes.add(sensorNode);
        }

        return nodes;
    }
}
