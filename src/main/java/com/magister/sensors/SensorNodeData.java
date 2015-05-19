package com.magister.sensors;

import java.util.Date;

public class SensorNodeData {
    private final SensorNode sensorNode;
    private final SensorType sensorType;
    private final Date timestamp;
    private final Object sensorValue;

    public SensorNodeData(SensorNode sensorNode, SensorType sensorType,
            Date timestamp, Object sensorValue) {
        this.sensorNode = sensorNode;
        this.sensorType = sensorType;
        this.timestamp = timestamp;
        this.sensorValue = sensorValue;
    }

    public SensorNode getSensorNode() {
        return sensorNode;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Object getSensorValue() {
        return sensorValue;
    }
}
