package com.tsi.vehicle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vin;

    private String registrationNumber;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    private String engineNumber;

    private String brand;

    private String model;

    private String colour;

    @Temporal(TemporalType.DATE)
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

