package com.example.demoProyect.api.v1.model.data;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "dataunit")
public class DataUnit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false, unique = true)
	private String symbol;	
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "dataUnit")
    private Set<Sensor> sensors;
	
	
	
	
	public DataUnit() {}
	public DataUnit(String id, String symbol, String name, Set<Sensor> sensors) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.name = name;
		this.sensors = sensors;
	}


	public String getId() {	return id; }
	public void setId(String id) { this.id = id; }
	
	public String getSymbol() { return symbol; }
	public void setSymbol(String symbol) { this.symbol = symbol; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public Set<Sensor> getSensors() { return sensors; }
	public void setSensors(Set<Sensor> sensors) { this.sensors = sensors; }
	
	
	@Override
    public String toString() {
        return "DataUnit{" +
               "id=" + id +
               ", symbol='" + symbol + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
