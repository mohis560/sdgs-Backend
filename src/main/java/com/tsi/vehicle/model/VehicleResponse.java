package com.tsi.vehicle.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Builder
public class VehicleResponse {

    private String vin;

    private String registrationNumber;

    private Date registrationDate;

    private String engineNumber;

    private String brand;

    private String model;

    private String colour;

    private Date manufacturingDate;

    private String vehicleType;

    private String emissionClass;

    private Double odometerReading;

    private String engineType;

    private String transmissionType;

    private String serviceHistory;

    private Double price;

    private String ownershipHistory;

    private String warrantyInformation;

    private String sellingDealer;

    private String featuresAndOptions;

    private Integer yearOfTheVehicle;

    private String vehicleCondition;

    private String vehicleLocation;

    private String status;

}