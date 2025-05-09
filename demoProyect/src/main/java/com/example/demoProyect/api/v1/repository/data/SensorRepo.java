package com.example.demoProyect.api.v1.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.data.Sensor;


@Repository
public interface SensorRepo  extends JpaRepository<Sensor, String>{

}
