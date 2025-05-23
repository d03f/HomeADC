package com.homeadc.homeadc.api.v1.repository.data;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.homeadc.homeadc.api.v1.model.data.SensorRecord;


@Repository
public interface SensorRecordRepo  extends JpaRepository<SensorRecord, String>, JpaSpecificationExecutor<SensorRecord> {
	
	Optional<SensorRecord> findTopBySensor_NameOrderByValueDesc(String sensorName);
	
	Optional<SensorRecord> findTopBySensor_NameOrderByValueAsc(String sensorName);
	
	 @Query("SELECT AVG(sr.value) FROM SensorRecord sr WHERE sr.sensor.name = :sensorName") // Removed owner filter from JPQL
    Optional<BigDecimal> findAverageValueBySensorName(@Param("sensorName") String sensorName); // Removed ownerAccountKey parameter




}
