package com.example.demoProyect.api.v1.model.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demoProyect.api.v1.model.authentication.ApiKey;
import com.example.demoProyect.api.v1.model.authentication.ApiUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor")
public class Sensor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String sensorId;
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = true)
	private String description;
	@Column(nullable = true)
	private String location;
	
	@ManyToOne
	@JoinColumn(name = "data_unit_id", nullable = false)
	private DataUnit dataUnit;
	
	
	@Column(nullable = false)
	private LocalDateTime creationDate;
	@Column(nullable = false)
	private LocalDateTime lastActivity;

	@ManyToOne
	@JoinColumn(name = "owner_user_id", nullable = false)
	private ApiUser owner;
	
	@ManyToMany
	@JoinTable(name = "sensor_allowed_apikeys",
				joinColumns = @JoinColumn(name = "sensor_id"),
				inverseJoinColumns = @JoinColumn(name = "apikey_value") )
	private List<ApiKey> allowedApiKeys;

	@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Use CascadeType.ALL cautiously
    private List<SensorRecord> records;
	
	public Sensor() {}
	public Sensor(String sensorId, String name, String description, String location, DataUnit dataUnit,
			LocalDateTime creationDate, LocalDateTime lastActivity, ApiUser owner, List<ApiKey> allowedApiKeys, List<SensorRecord> records) {
		super();
		this.sensorId = sensorId;
		this.name = name;
		this.description = description;
		this.location = location;
		this.dataUnit = dataUnit;
		this.creationDate = creationDate;
		this.lastActivity = lastActivity;
		this.owner = owner;
		this.allowedApiKeys = allowedApiKeys;
		this.records = records;
	}
	
	
	
	public String getSensorId() {return sensorId; }
	public void setSensorId(String sensorId) {this.sensorId = sensorId; }
	
	public String getName() {return name; }
	public void setName(String name) {this.name = name; }
	
	public String getDescription() {return description; }
	public void setDescription(String description) {this.description = description; }
	
	public String getLocation() {return location; }
	public void setLocation(String location) {this.location = location; }
	
	public DataUnit getDataUnit() {return dataUnit; }
	public void setDataUnit(DataUnit dataUnit) {this.dataUnit = dataUnit; }
	
	public LocalDateTime getCreationDate() {return creationDate; }
	public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate; }
	
	public LocalDateTime getLastActivity() {return lastActivity; }
	public void setLastActivity(LocalDateTime lastActivity) {this.lastActivity = lastActivity; }
	
	public ApiUser getOwner() {return owner; }
	public void setOwner(ApiUser owner) {this.owner = owner; }
	
	public List<ApiKey> getAllowedApiKeys() {return allowedApiKeys; }
	public void setAllowedApiKeys(List<ApiKey> allowedApiKeys) {this.allowedApiKeys = allowedApiKeys; }
	
	public List<SensorRecord> getRecords() {return this.records;}
	
	
	public void addRecord(SensorRecord newRecord) {
		if (this.records == null) { this.records = new ArrayList<>(); }
		this.records.add(newRecord); newRecord.setSensor(this);
	}

	public void removeRecord(SensorRecord record) {
		if (this.records != null) { this.records.remove(record); record.setSensor(null); }
	}

	@Override
    public String toString() {
        return "Sensor{" +
               "sensorId='" + sensorId + '\'' +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", location='" + location + '\'' +
               ", dataUnit=" + (dataUnit != null ? "DataUnit{id=" + dataUnit.getId() + ", symbol='" + dataUnit.getSymbol() + "'}" : "null") + // Assuming DataUnit has getId() and getSymbol()
               ", creationDate=" + creationDate +
               ", lastActivity=" + lastActivity +
               ", owner=" + (owner != null ? "ApiUser{userAccountKey='" + owner.getUserAccountKey() + "', userName='" + owner.getUserName() + "'}" : "null") + // Assuming ApiUser has getUserAccountKey() and getUserName()
               ", allowedApiKeys=" + (allowedApiKeys != null ?
                                     allowedApiKeys.stream()
                                                   .map(key -> "ApiKey{value='" + key.getApiKeyValue() + "', name='" + key.getName() + "'}") // Assuming ApiKey has getApiKeyValue() and getName()
                                                   .collect(Collectors.joining(", ", "[", "]"))
                                     : "null") +
               ", recordsCount=" + (records != null ? records.size() : 0) + // Added count for records
               '}';
    }
}
