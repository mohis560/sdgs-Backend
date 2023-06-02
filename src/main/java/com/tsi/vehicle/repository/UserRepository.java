package com.tsi.vehicle.repository;

import com.tsi.vehicle.model.User;
import com.tsi.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
