package com.magister.db.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Mote {

    public static enum MoteType {
        TEMP, LIGHT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mote_id", nullable = false)
    private long id;

    @Column(name = "mote_power", nullable = false)
    private int power;

    @Column(name = "mote_latitude", nullable = false)
    private double latitude;

    @Column(name = "mote_longtitude", nullable = false)
    private double longtitude;

    @Column(name = "mote_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MoteType moteType;

    @Column(name = "mote_gateway", nullable = false)
    private boolean isGateway;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="mote_id", referencedColumnName="mote_id")
    private List<SensorData> metering;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isAlive() {
        return power > 0;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public MoteType getMoteType() {
        return moteType;
    }

    public void setMoteType(MoteType moteType) {
        this.moteType = moteType;
    }

    public boolean isGateway() {
        return isGateway;
    }

    public void setGateway(boolean isGateway) {
        this.isGateway = isGateway;
    }

    public List<SensorData> getMetering() {
        return metering;
    }

    public void setMetering(List<SensorData> metering) {
        this.metering = metering;
    }
}
