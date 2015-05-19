package com.magister.sensors;

/**
 * A sensor node, also known as a mote (chiefly in North America), is a node in a sensor network that is capable of
 * performing some processing, gathering sensory information and communicating with other connected nodes in the
 * network. A mote is a node but a node is not always a mote.
 */
public class SensorNode {

    private final int nodeId;

    /** Long range communication with gateway */
    private final SensorNetworkGateway gateway;

    public SensorNode(int nodeId, SensorNetworkGateway gateway) {
        this.nodeId = nodeId;
        this.gateway = gateway;
    }

    // TODO: actually we should have different types of sensor nodes
    // and configure schedule of sensor wake ups
    // when sensor wakeups, send event to gateway

    public int getNodeId() {
        return nodeId;
    }
}
