package com.example.demoProyect.api.v1.model.data.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.demoProyect.api.v1.model.data.SensorRecord;


public class SensorRecordDTO {

    private String sensorRecordId;
    private BigDecimal value;
    private String sensorId;
    private LocalDateTime timestamp;
    private String metadata;


    public SensorRecordDTO() {}
    public SensorRecordDTO(SensorRecord sensorRecord) {
        if (sensorRecord != null) {
            this.sensorRecordId = sensorRecord.getSensorRecordId();
            this.value = sensorRecord.getValue();

            if (sensorRecord.getSensor() != null) { this.sensorId = sensorRecord.getSensor().getSensorId(); }

            this.timestamp = sensorRecord.getTimestamp();
            this.metadata = sensorRecord.getMetadata();
        }
    }



    public String getSensorRecordId() { return sensorRecordId; }
    public void setSensorRecordId(String sensorRecordId) { this.sensorRecordId = sensorRecordId; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }


    @Override
    public String toString() {
        return "SensorRecordDto{" +
               "sensorRecordId='" + sensorRecordId + '\'' +
               ", value=" + value +
               ", sensorId='" + sensorId + '\'' +
               ", timestamp=" + timestamp +
               ", metadata='" + metadata + '\'' +
               '}';
    }
}