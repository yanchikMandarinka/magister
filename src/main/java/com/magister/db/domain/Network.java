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
public class Network {

    public static enum Mode {
        AUTOMATIC, MANUAL;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "network_id", nullable = false)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="network_id", referencedColumnName="network_id")
    private List<Mote> motes;

    @Column(name = "network_name", nullable = false)
    private String name;

    @Column(name = "network_mode", nullable = false)
    @Enumerated(EnumType.STRING)
    private Mode mode;

    @Column(name = "enabled")
    private boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mote> getMotes() {
        return motes;
    }

    public void setMotes(List<Mote> motes) {
        this.motes = motes;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Network [id=" + id + ", name=" + name + ", mode=" + mode + ", enabled=" + enabled + "]";
    }
}
