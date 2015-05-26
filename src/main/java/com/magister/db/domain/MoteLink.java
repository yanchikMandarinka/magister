package com.magister.db.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(MoteLinkPK.class)
public class MoteLink {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private Mote source;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private Mote target;

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

    @Override
    public String toString() {
        return "MoteLink [source=" + source + ", target=" + target + "]";
    }


}
