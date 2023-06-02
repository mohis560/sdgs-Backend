package com.tsi.vehicle.services;

import com.tsi.vehicle.model.VehicleFormRequest;
import com.tsi.vehicle.model.VehicleResponse;
import org.springframework.http.ResponseEntity;

public interface VehicleService {

    VehicleResponse save(VehicleFormRequest vehicle);

    boolean isValidAddVehicleRequest(VehicleFormRequest vehicleFormRequest);

    ResponseEntity fetchDetailsByVin(String vin);
}
