package com.tsi.vehicle.services.impl;

import com.tsi.vehicle.exception.VehicleNotFoundException;
import com.tsi.vehicle.model.Vehicle;
import com.tsi.vehicle.model.VehicleFormRequest;
import com.tsi.vehicle.model.VehicleResponse;
import com.tsi.vehicle.repository.VehicleRepository;
import com.tsi.vehicle.services.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public VehicleResponse save(VehicleFormRequest vehicleFormRequest) {
        Vehicle vehicle = buildVehicleEntityFromRequest(vehicleFormRequest);
        vehicle = vehicleRepository.save(vehicle);
        VehicleResponse vehicleResponse = buildVehicleResponse(vehicle);
        return vehicleResponse;
    }

    private static Vehicle buildVehicleEntityFromRequest(VehicleFormRequest vehicleFormRequest) {
        Vehicle vehicle = Vehicle.builder()
                .vin(vehicleFormRequest.getVin())
                .registrationNumber(vehicleFormRequest.getRegistrationNumber())
                .registrationDate(vehicleFormRequest.getRegistrationDate())
                .engineNumber(vehicleFormRequest.getEngineNumber())
                .brand(vehicleFormRequest.getBrand())
                .model(vehicleFormRequest.getModel())
                .colour(vehicleFormRequest.getColour())
                .manufacturingDate(vehicleFormRequest.getManufacturingDate())
                .vehicleType(vehicleFormRequest.getVehicleType())
                .emissionClass(vehicleFormRequest.getEmissionClass())
                .odometerReading(vehicleFormRequest.getOdometerReading())
                .engineType(vehicleFormRequest.getEngineType())
                .transmissionType(vehicleFormRequest.getTransmissionType())
                .serviceHistory(vehicleFormRequest.getServiceHistory())
                .price(vehicleFormRequest.getPrice())
                .ownershipHistory(vehicleFormRequest.getOwnershipHistory())
                .warrantyInformation(vehicleFormRequest.getWarrantyInformation())
                .sellingDealer(vehicleFormRequest.getSellingDealer())
                .featuresAndOptions(vehicleFormRequest.getFeaturesAndOptions())
                .yearOfTheVehicle(vehicleFormRequest.getYearOfTheVehicle())
                .vehicleCondition(vehicleFormRequest.getVehicleCondition())
                .vehicleLocation(vehicleFormRequest.getVehicleLocation())
                .status(vehicleFormRequest.getStatus())
                .build();
        return vehicle;
    }

    private static VehicleResponse buildVehicleResponse(Vehicle vehicle) {
        VehicleResponse vehicleResponse =
                VehicleResponse.builder()
                        .vin(vehicle.getVin())
                        .registrationNumber(vehicle.getRegistrationNumber())
                        .registrationDate(vehicle.getRegistrationDate())
                        .engineNumber(vehicle.getEngineNumber())
                        .brand(vehicle.getBrand())
                        .model(vehicle.getModel())
                        .colour(vehicle.getColour())
                        .manufacturingDate(vehicle.getManufacturingDate())
                        .vehicleType(vehicle.getVehicleType())
                        .emissionClass(vehicle.getEmissionClass())
                        .odometerReading(vehicle.getOdometerReading())
                        .engineType(vehicle.getEngineType())
                        .transmissionType(vehicle.getTransmissionType())
                        .serviceHistory(vehicle.getServiceHistory())
                        .price(vehicle.getPrice())
                        .ownershipHistory(vehicle.getOwnershipHistory())
                        .warrantyInformation(vehicle.getWarrantyInformation())
                        .sellingDealer(vehicle.getSellingDealer())
                        .featuresAndOptions(vehicle.getFeaturesAndOptions())
                        .yearOfTheVehicle(vehicle.getYearOfTheVehicle())
                        .vehicleCondition(vehicle.getVehicleCondition())
                        .vehicleLocation(vehicle.getVehicleLocation()).status(vehicle.getStatus())
                        .build();
        return vehicleResponse;
    }

    @Override
    public boolean isValidAddVehicleRequest(VehicleFormRequest vehicleFormRequest) {
        String vin = vehicleFormRequest.getVin();
        String registrationNumber = vehicleFormRequest.getRegistrationNumber();
        String vehicleType = vehicleFormRequest.getVehicleType();
        Date manufacturingDate = vehicleFormRequest.getManufacturingDate();
        String brand = vehicleFormRequest.getBrand();
        String engineNumber = vehicleFormRequest.getEngineNumber();
        Date registrationDate = vehicleFormRequest.getRegistrationDate();
        Double price = vehicleFormRequest.getPrice();
        String featuresAndOptions = vehicleFormRequest.getFeaturesAndOptions();
        boolean status = true;
        if (StringUtils.isEmpty(vin)
                || StringUtils.isEmpty(registrationNumber)
                || StringUtils.isEmpty(vehicleType)
                || manufacturingDate == null
                || StringUtils.isEmpty(brand)
                || StringUtils.isEmpty(engineNumber)
                || registrationDate == null
                || StringUtils.isEmpty(featuresAndOptions)
                || price == null
        ) {
            status = false;
        }
        if (StringUtils.isNotEmpty(vin) && vin.length() != 17) {
            status = false;
        }
        return status;
    }

    @Override
    public ResponseEntity fetchDetailsByVin(String vin) {
        ResponseEntity response = null;
        try {
            boolean vehicleExist = vehicleRepository.existsByVin(vin);
            if (vehicleExist) {
                Vehicle vehicle = vehicleRepository.findByVin(vin);
                VehicleResponse vehicleResponse = buildVehicleResponse(vehicle);
                response = new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
            } else {
                throw new VehicleNotFoundException("Vehicle with " + vin + " Not Found.");
            }
        } catch (VehicleNotFoundException e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            log.error("Internal Server Error Occurred " + e);
        } catch (Exception e) {
            log.error("Internal Server Error Occurred " + e);
        } finally {
            return response;
        }
    }
}
