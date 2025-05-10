package com.example.demoProyect.api.v1.model.data.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.demoProyect.api.v1.model.data.SensorRecord;


public class SensorRecordDTO {

    private BigDecimal value;
    private LocalDateTime timestamp;
    private String metadata;


    public SensorRecordDTO() {}
    public SensorRecordDTO(SensorRecord sensorRecord) {
        if (sensorRecord != null) {
            this.value = sensorRecord.getValue();


            this.timestamp = sensorRecord.getTimestamp();
            this.metadata = sensorRecord.getMetadata();
        }
    }




    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }


    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }


    @Override
    public String toString() {
        return "SensorRecordDto{" +
               ", value=" + value +
               ", timestamp=" + timestamp +
               ", metadata='" + metadata + '\'' +
               '}';
    }
}