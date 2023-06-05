package com.tsi.vehicle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Where(clause="active_flag=1")
@Table(name = "vehicle")

public class Vehicle extends BaseEntity {


    private Long id;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{17}$",message = "Invalid Vin")
    @Column(unique = true,length =17)
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
