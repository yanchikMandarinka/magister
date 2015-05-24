package com.magister.db.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MoteLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Mote source;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Mote target;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mote getSource() {
        return source;
    }

    public void setSource(Mote source) {
        this.source = source;
    }

    public Mote getTarget() {
        return target;
    }

    public void setTarget(Mote target) {
        this.target = target;
    }
}
