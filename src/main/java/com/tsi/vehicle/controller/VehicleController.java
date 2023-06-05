package com.tsi.vehicle.controller;

import com.tsi.vehicle.dto.SearchByAttrResponseDTO;
import com.tsi.vehicle.dto.SearchByAttributesRequestDTO;
import com.tsi.vehicle.exception.VehicleNotFoundException;
import com.tsi.vehicle.model.Vehicle;
import com.tsi.vehicle.model.VehicleFormRequest;
import com.tsi.vehicle.model.VehicleResponse;
import com.tsi.vehicle.services.impl.VehicleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/vehicles")
@CrossOrigin
@Slf4j

public class VehicleController {

    @Autowired
    private VehicleServiceImpl vehicleService;

    @Value("${vin.length.limit:17}")
    private Integer validVinLength;


    @PostMapping
    public ResponseEntity saveVehicle(@RequestBody  VehicleFormRequest vehicleFormRequest) throws ParseException {

        if (Objects.isNull(vehicleFormRequest)) {
            return ResponseEntity.badRequest().build();
        } else if (!vehicleService.isValidAddVehicleRequest(vehicleFormRequest)) {
            return ResponseEntity.badRequest().build();
        }
        VehicleResponse vehicleResponse = vehicleService.save(vehicleFormRequest);
        return ResponseEntity.ok().body(vehicleResponse);
    }

    @PutMapping("/{vin}")
    public ResponseEntity updateVehicle(@PathVariable("vin") String vin,
                                        @RequestBody VehicleFormRequest vehicleFormRequest) {
        if (Objects.isNull(vehicleFormRequest)) {
            return ResponseEntity.badRequest().build();
        } else if (!vehicleService.isVinPresent(vin)) {
            return ResponseEntity.badRequest().build();
        } else if (!vehicleService.isValidAddVehicleRequest(vehicleFormRequest)) {
            return ResponseEntity.badRequest().build();
        }
        vehicleFormRequest.setVin(vin);
        VehicleResponse vehicleResponse = vehicleService.update(vehicleFormRequest);
        return ResponseEntity.ok().body(vehicleResponse);
    }

    @GetMapping("/{vin}")
    public ResponseEntity getDetailsByVin(@PathVariable("vin") String vin) {
        log.info("getDetailsByVin invoked");
        boolean isValidVin = validVinLength == vin.length() && StringUtils.isAlphanumeric(vin);
        if (StringUtils.isNotBlank(vin) && isValidVin) {
            return vehicleService.fetchDetailsByVin(vin);
        } else {
            throw new VehicleNotFoundException("Invalid VIN");
        }
    }

    @DeleteMapping("/{vin}")
    public ResponseEntity<String> delete(@PathVariable("vin") String vin) {
        vehicleService.deleteVehicle(vin);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PostMapping("/byAttributes")
    public ResponseEntity<SearchByAttrResponseDTO> getDetailsByAttributes(@RequestBody SearchByAttributesRequestDTO attrRequestDTO){
        log.info("getDetailsByAttributes invoked");

        HttpStatus statusCode = HttpStatus.OK;
        SearchByAttrResponseDTO searchByAttrResponse = new SearchByAttrResponseDTO(null, null,false,null,0);

        if(null!=attrRequestDTO){
            List<Vehicle> vehicleResponseList = vehicleService.fetchDetailsByAttr(attrRequestDTO);
            if(vehicleResponseList.isEmpty()){
                statusCode = HttpStatus.NOT_FOUND;
                searchByAttrResponse.setSuccessMsg("FAILURE");
            } else {
                searchByAttrResponse.setVehicleList(vehicleResponseList);
                searchByAttrResponse.setVehicleListCount(vehicleResponseList.size());
                searchByAttrResponse.setSuccessFlag(true);
                searchByAttrResponse.setSuccessMsg("SUCCESS");
            }
        } else {
            statusCode = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(searchByAttrResponse, statusCode);
    }

}