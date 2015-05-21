package com.magister.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.magister.db.domain.SensorData;

public interface SensorDataRepository extends CrudRepository<SensorData, Long>{
}
