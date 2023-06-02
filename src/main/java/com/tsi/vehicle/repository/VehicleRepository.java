package com.tsi.vehicle.repository;

import com.tsi.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByVin(String vin);

    int countByVin(String vin);
    boolean existsByVin(String vin);

}

