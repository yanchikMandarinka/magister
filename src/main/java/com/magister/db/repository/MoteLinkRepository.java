package com.magister.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;

public interface MoteLinkRepository extends CrudRepository<MoteLink, Long>{
    MoteLink findBySource(Mote source);
}
