package com.magister.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;

public interface NetworkRepository extends CrudRepository<Network, Long> {

    Network findByMotes(Mote mote);
}
