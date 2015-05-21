package com.magister.db.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "network_id")
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="network_id", referencedColumnName="network_id")
    private List<Mote> motes;

    @Column(name = "network_name")
    private String name;

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
}
