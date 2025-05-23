package com.homeadc.homeadc.api.v1.model.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensorrecord")
public class SensorRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String sensorRecordId;
	@Column(nullable = false)
	private BigDecimal value;
	
	@ManyToOne
	@JoinColumn(name = "sensor_id", nullable = false)
	private Sensor sensor;
	
	@Column(nullable = false)
	private LocalDateTime timestamp;
	@Column(nullable = true)
	private String metadata;
	
	
	
	public SensorRecord() {}
	public SensorRecord(String sensorRecordId, BigDecimal value, Sensor sensor, LocalDateTime timestamp, String metadata) {
		super();
		this.sensorRecordId = sensorRecordId;
		this.value = value;
		this.sensor = sensor;
		this.timestamp = timestamp;
		this.metadata = metadata;
	}
	
	
	
	public String getSensorRecordId() {  return sensorRecordId;  }
	public void setSensorRecordId(String sensorRecordId) {  this.sensorRecordId = sensorRecordId;  }
	
	public BigDecimal getValue() {  return value;  }
	public void setValue(BigDecimal value) {  this.value = value;  }
	
	public Sensor getSensor() {  return sensor;  }
	public void setSensor(Sensor sensor) {  this.sensor = sensor;  }
	
	public LocalDateTime getTimestamp() {  return timestamp;  }
	public void setTimestamp(LocalDateTime timestamp) {  this.timestamp = timestamp;  }
	
	public String getMetadata() {  return metadata;  }
	public void setMetadata(String metadata) {  this.metadata = metadata;  }
	
   	
	
	
	@Override
    public String toString() {
        // Use null-safe checks and include relevant identifying info for the related Sensor
        return "SensorRecord{" +
               "sensorRecordId='" + sensorRecordId + '\'' +
               ", value='" + value + '\'' +
               // Include only identifying info for the related Sensor to prevent recursion and lazy loading issues
               ", sensor=" + (sensor != null ? "Sensor{sensorId='" + sensor.getSensorId() + "', name='" + sensor.getName() + "'}" : "null") + // Assuming Sensor has getSensorId() and getName()
               ", dateTime=" + timestamp +
               ", metadata='" + metadata + '\'' +
               '}';
    }
	
	
}
