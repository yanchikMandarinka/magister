package com.magister.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.magister.db.domain.MoteLink;

public interface MoteLinkRepository extends CrudRepository<MoteLink, Long>{
    MoteLink findBySourceId(Long id);
}
