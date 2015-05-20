package com.magister.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.magister.db.domain.Mote;

public interface MoteRepository extends CrudRepository<Mote, Long>{
}
