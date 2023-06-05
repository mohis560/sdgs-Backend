package com.tsi.vehicle.dto;

import com.tsi.vehicle.model.Vehicle;

import java.util.List;

public class SearchByAttrResponseDTO extends ResponseOutputDTO {

    private List<Vehicle> vehicleList;

    private int vehicleListCount;

    public int getVehicleListCount() {
        return vehicleListCount;
    }

    public void setVehicleListCount(int vehicleListCount) {
        this.vehicleListCount = vehicleListCount;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }
    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public SearchByAttrResponseDTO(String userId, String successMsg, boolean successFlag, List<Vehicle> vehicleList, int vehicleListCount) {
        super(userId, successMsg, successFlag);
        this.vehicleList = vehicleList;
        this.vehicleListCount = vehicleListCount;
    }

}
