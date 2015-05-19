package com.magister.sensors;

import java.util.Collections;
import java.util.List;

public class WirelessSensorNetwork {

    private SensorNetworkGateway gateway;
    private List<SensorNode> sensorNodes;

    public WirelessSensorNetwork(SensorNetworkGateway gateway, List<SensorNode> sensorNodes) {
        this.gateway = gateway;
        this.sensorNodes = sensorNodes;
    }

    /**
     * @return unmodifiable list of sensors in the network
     */
    public List<SensorNode> getSensorNodes() {
        return Collections.unmodifiableList(sensorNodes);
    }
}
