package com.magister.db.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Mote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
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

    @Column(name = "mote_delay", nullable = false)
    private long delay;

    @Column(name = "mote_gateway", nullable = false)
    private boolean isGateway;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("timestamp")
    private Set<SensorData> metering = new HashSet<>();

    @Column(name="mote_broken")
    private boolean broken;

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

    /**
     * Mote considered as alive if it has power
     * or it is gateway (unlimited power)
     * @return
     */
    public boolean isAlive() {
        boolean hasPower = (power > 0 || isGateway);
        return hasPower && !broken;
    }

    public double getLatitude() {
        return latitude;
    }

    /**
     * The valid range of latitude in degrees is -90 and +90
     * for the southern and northern hemisphere respectively.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    /**
     * Longitude is in the range -180 and +180 specifying the east-west position.
     */
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public MoteType getMoteType() {
        return moteType;
    }

    public void setMoteType(MoteType moteType) {
        this.moteType = moteType;
    }

    /**
     * Delay in seconds. Mote sleeps for delay and than gather sensors data and sends the data.
     */
    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean isGateway() {
        return isGateway;
    }

    public void setGateway(boolean isGateway) {
        this.isGateway = isGateway;
    }

    public Set<SensorData> getMetering() {
        return metering;
    }

    public void setMetering(Set<SensorData> metering) {
        this.metering = metering;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    @Override
    public String toString() {
        return "Mote [id=" + id
                + ", power=" + power
                + ", delay=" + delay
                + ", type=" + moteType
                + ", isGateway=" + isGateway
                + ", isBroken=" + isBroken()
                + ", isAlive=" + isAlive()
                + "]";
    }
}
