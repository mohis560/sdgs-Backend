package com.tsi.vehicle.repository;

import com.tsi.vehicle.dto.SearchByAttributesRequestDTO;
import com.tsi.vehicle.model.Vehicle;

import java.util.Date;
import java.util.List;

public interface VehicleRepoCustom {

    List<Vehicle> searchAllByAttributes(boolean vehicleTypeExist, boolean engineTypeExist, boolean vehicleCondExist,
                                        boolean mfgDateExist, SearchByAttributesRequestDTO attrRequestDTO);

}
