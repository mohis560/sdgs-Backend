package com.tsi.vehicle.services.impl;

import com.tsi.vehicle.dto.SearchByAttributesRequestDTO;
import com.tsi.vehicle.exception.VehicleNotFoundException;
import com.tsi.vehicle.model.Vehicle;
import com.tsi.vehicle.model.VehicleFormRequest;
import com.tsi.vehicle.model.VehicleResponse;
import com.tsi.vehicle.repository.VehicleRepository;
import com.tsi.vehicle.services.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public VehicleResponse save(VehicleFormRequest vehicleFormRequest) throws ConstraintViolationException {
        Vehicle vehicle = buildVehicleEntityFromRequest(vehicleFormRequest);
        try{
            vehicle=vehicleRepository.save(vehicle);

        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("VIN is already exists");
        }

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
        if (StringUtils.isNotEmpty(vin) && (vin.length() != 17)){

            status = false;
        }
        return status;
    }

    @Override
    public ResponseEntity fetchDetailsByVin(String vin) {
        log.info("fetchDetailsByVin invoked");
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

    @Override
    @Transactional
    public void deleteVehicle(String vin) {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        vehicle.setActiveFlag(Boolean.FALSE);
        vehicleRepository.save(vehicle);
    }

    public boolean isVinPresent(String vin) {
       return vehicleRepository.existsByVin(vin);
    }

    public boolean isVinDuplicate(String vin){
        return vehicleRepository.existsByVin(vin);
    }

    @Override
    public VehicleResponse update(VehicleFormRequest vehicleFormRequest) {
        //old vehicle.
        Vehicle vehicle = vehicleRepository.findByVin(vehicleFormRequest.getVin());
        //update the old vehicle data wit new data.
        vehicle = updateVehicleEntity(vehicleFormRequest, vehicle);
        vehicle = vehicleRepository.save(vehicle);
        return buildVehicleResponse(vehicle);
    }

    private Vehicle updateVehicleEntity(VehicleFormRequest vehicleFormRequest,Vehicle vehicle) {
        vehicle.setBrand(vehicleFormRequest.getBrand());
        vehicle.setModel(vehicleFormRequest.getModel());
        vehicle.setColour(vehicleFormRequest.getColour());
        vehicle.setVehicleType(vehicleFormRequest.getVehicleType());
        vehicle.setEmissionClass(vehicleFormRequest.getEmissionClass());
        vehicle.setOdometerReading(vehicleFormRequest.getOdometerReading());
        vehicle.setEngineType(vehicleFormRequest.getEngineType());
        vehicle.setTransmissionType(vehicleFormRequest.getTransmissionType());
        vehicle.setServiceHistory(vehicleFormRequest.getServiceHistory());
        vehicle.setPrice(vehicleFormRequest.getPrice());
        vehicle.setOwnershipHistory(vehicleFormRequest.getOwnershipHistory());
        vehicle.setWarrantyInformation(vehicleFormRequest.getWarrantyInformation());
        vehicle.setSellingDealer(vehicleFormRequest.getSellingDealer());
        vehicle.setFeaturesAndOptions(vehicleFormRequest.getFeaturesAndOptions());
        vehicle.setYearOfTheVehicle(vehicleFormRequest.getYearOfTheVehicle());
        vehicle.setVehicleCondition(vehicleFormRequest.getVehicleCondition());
        vehicle.setVehicleLocation(vehicleFormRequest.getVehicleLocation());
        vehicle.setStatus(vehicleFormRequest.getStatus());

        return vehicle;
    }

    @Override
    public List<Vehicle> fetchDetailsByAttr(SearchByAttributesRequestDTO attrRequestDTO) {
        log.info("fetchDetailsByAttr invoked");
        List<Vehicle> vehicleList = new ArrayList<>();

        try{
            boolean vehicleTypePresent = StringUtils.isNotBlank(attrRequestDTO.getVehicleType());
            boolean engineTypePresent = StringUtils.isNotBlank(attrRequestDTO.getEngineType());
            boolean vehicleCondPresent = StringUtils.isNotBlank(attrRequestDTO.getVehicleCondition());
            boolean mfgDatePresent = null!=attrRequestDTO.getManufacturingDate();

            vehicleList = vehicleRepository.searchAllByAttributes(vehicleTypePresent,
                    engineTypePresent, vehicleCondPresent,
                    mfgDatePresent, attrRequestDTO);

        } catch(Exception e){
            log.error(e+" Occurred");
        }
        return vehicleList;
    }
}
