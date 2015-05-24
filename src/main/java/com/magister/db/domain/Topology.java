package com.magister.db.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Topology {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MoteLink> links = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MoteLink> getLinks() {
        return links;
    }

    public void setLinks(List<MoteLink> links) {
        this.links = links;
    }

}
