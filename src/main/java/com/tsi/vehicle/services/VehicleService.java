package com.tsi.vehicle.services;

import com.tsi.vehicle.dto.SearchByAttributesRequestDTO;
import com.tsi.vehicle.model.Vehicle;
import com.tsi.vehicle.model.VehicleFormRequest;
import com.tsi.vehicle.model.VehicleResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface VehicleService {

    VehicleResponse save(VehicleFormRequest vehicle);

    boolean isValidAddVehicleRequest(VehicleFormRequest vehicleFormRequest);

    ResponseEntity fetchDetailsByVin(String vin);

    void deleteVehicle(String vin);

    List<Vehicle> fetchDetailsByAttr(SearchByAttributesRequestDTO attrRequestDTO);

    boolean isVinPresent(String vin);

    VehicleResponse update(VehicleFormRequest vehicleFormRequest);
}
