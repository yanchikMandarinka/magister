package com.magister.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mote {

    public static enum MoteType {
        TEMP, LIGHT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "mote_power")
    private int power;

    @Column(name = "mote_latitude")
    private double latitude;

    @Column(name = "mote_longtitude")
    private double longtitude;

    @Column(name = "mote_type")
    private MoteType moteType;

    @Column(name = "mote_gateway")
    private boolean isGateway;

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
}
