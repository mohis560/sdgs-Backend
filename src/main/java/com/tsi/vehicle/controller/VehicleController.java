package com.tsi.vehicle.controller;


import com.tsi.vehicle.exception.VehicleNotFoundException;
import com.tsi.vehicle.model.VehicleFormRequest;
import com.tsi.vehicle.model.VehicleResponse;
import com.tsi.vehicle.services.impl.VehicleServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Objects;

@RestController
@RequestMapping("/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleServiceImpl vehicleService;

    @Value("${vin.length.limit:17}")
    private Integer validVinLength;

    @PostMapping
    public ResponseEntity saveVehicle(@RequestBody VehicleFormRequest vehicleFormRequest) throws ParseException {
        if (Objects.isNull(vehicleFormRequest)) {
            return ResponseEntity.badRequest().build();
        } else if (!vehicleService.isValidAddVehicleRequest(vehicleFormRequest)) {
            return ResponseEntity.badRequest().build();
        }
        VehicleResponse vehicleResponse = vehicleService.save(vehicleFormRequest);
        return ResponseEntity.ok().body(vehicleResponse);
    }

    @GetMapping("/{vin}")
    public ResponseEntity getDetailsByVin(@PathVariable("vin") String vin) {
        boolean isValidVin = validVinLength == vin.length() && StringUtils.isAlphanumeric(vin);
        if (StringUtils.isNotBlank(vin) && isValidVin) {
            return vehicleService.fetchDetailsByVin(vin);
        } else {
            throw new VehicleNotFoundException("Invalid VIN");
        }
    }

}