package com.example.demoProyect.api.v1.model.data.dto;

import com.example.demoProyect.api.v1.model.data.DataUnit;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "symbol"})
public class DataUnitDTO {

    private String symbol;
    private String name;


    public DataUnitDTO() {}
    public DataUnitDTO(DataUnit dataUnit) {
        if (dataUnit != null) {
            this.symbol = dataUnit.getSymbol();
            this.name = dataUnit.getName();
        }
    }




    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    
    
    @Override
    public String toString() {
        return "DataUnitDto{" +
               ", symbol='" + symbol + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}