package com.example.demoProyect.api.v1.model.data.dto;

import java.time.LocalDateTime;
import com.example.demoProyect.api.v1.model.data.Sensor;
import com.fasterxml.jackson.annotation.JsonPropertyOrder; 

@JsonPropertyOrder({
	"sensorId", "name", 
	"description", "location",
	"dataUnit", "ownerUserId",
	"ownerUserName", "creationDate",
	"lastActivity", "allowedApiKeysCount",
	"recordsCount"})
public class SensorDTO {

    private String sensorId;
    private String name;
    private String description;
    private String location;

    private DataUnitDTO dataUnit;

    private String ownerUserId;
    private String ownerUserName;

    private LocalDateTime creationDate;
    private LocalDateTime lastActivity;

    private int allowedApiKeysCount;
    private int recordsCount;



    public SensorDTO() {}
    public SensorDTO(Sensor sensor) {
        if (sensor != null) {
            this.sensorId = sensor.getSensorId();
            this.name = sensor.getName();
            this.description = sensor.getDescription();
            this.location = sensor.getLocation();

            if (sensor.getDataUnit() != null) { this.dataUnit = new DataUnitDTO(sensor.getDataUnit()); }

            if (sensor.getOwner() != null) { this.ownerUserId = sensor.getOwner().getUserAccountKey();  this.ownerUserName = sensor.getOwner().getUserName(); }

            this.creationDate = sensor.getCreationDate();
            this.lastActivity = sensor.getLastActivity();

            this.allowedApiKeysCount = (sensor.getAllowedApiKeys() != null) ? sensor.getAllowedApiKeys().size() : 0;
            this.recordsCount = (sensor.getRecords() != null) ? sensor.getRecords().size() : 0;
        }
    }


    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public DataUnitDTO getDataUnit() { return dataUnit; }
    public void setDataUnit(DataUnitDTO dataUnit) { this.dataUnit = dataUnit; }

    public String getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(String ownerUserId) { this.ownerUserId = ownerUserId; }

    public String getOwnerUserName() { return ownerUserName; }
    public void setOwnerUserName(String ownerUserName) { this.ownerUserName = ownerUserName; }


    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public LocalDateTime getLastActivity() { return lastActivity; }
    public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }

    public int getAllowedApiKeysCount() { return allowedApiKeysCount; }
    public void setAllowedApiKeysCount(int allowedApiKeysCount) { this.allowedApiKeysCount = allowedApiKeysCount; }

    public int getRecordsCount() { return recordsCount; }
    public void setRecordsCount(int recordsCount) { this.recordsCount = recordsCount; }


    @Override
    public String toString() {
        return "SensorDto{" +
               "sensorId='" + sensorId + '\'' +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", location='" + location + '\'' +
               ", dataUnit=" + dataUnit + // DataUnitDto's toString will be used
               ", ownerUserId='" + ownerUserId + '\'' +
               ", ownerUserName='" + ownerUserName + '\'' +
               ", creationDate=" + creationDate +
               ", lastActivity=" + lastActivity +
               ", allowedApiKeysCount=" + allowedApiKeysCount +
               ", recordsCount=" + recordsCount +
               '}';
    }
}