package com.tsi.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class SearchByAttributesRequestDTO {

    @JsonProperty("vehicleType")
    private String vehicleType;

    @JsonProperty("engineType")
    private String engineType;

    @JsonProperty("vehicleCondition")
    private String vehicleCondition;

    @JsonProperty("manufacturingDate")
    private Date manufacturingDate;

    public SearchByAttributesRequestDTO() {
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getVehicleCondition() {
        return vehicleCondition;
    }

    public void setVehicleCondition(String vehicleCondition) {
        this.vehicleCondition = vehicleCondition;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public SearchByAttributesRequestDTO(String vehicleType, String engineType, String vehicleCondition, Date manufacturingDate) {
        this.vehicleType = vehicleType;
        this.engineType = engineType;
        this.vehicleCondition = vehicleCondition;
        this.manufacturingDate = manufacturingDate;
    }
}
