package com.example.demoProyect.api.v1.model.data.dto;

import com.example.demoProyect.api.v1.model.data.DataUnit;

public class DataUnitDTO {

    private String id;
    private String symbol;
    private String name;


    public DataUnitDTO() {}
    public DataUnitDTO(DataUnit dataUnit) {
        if (dataUnit != null) {
            this.id = dataUnit.getId();
            this.symbol = dataUnit.getSymbol();
            this.name = dataUnit.getName();
        }
    }



    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    
    
    @Override
    public String toString() {
        return "DataUnitDto{" +
               "id='" + id + '\'' +
               ", symbol='" + symbol + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}