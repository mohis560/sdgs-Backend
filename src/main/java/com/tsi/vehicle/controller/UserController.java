package com.tsi.vehicle.controller;

import com.tsi.vehicle.model.User;
import com.tsi.vehicle.services.UserService;
import com.tsi.vehicle.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/getUsers")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/allUsers/{username}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable("username") String username) {
        List<User> allUser = userServiceImpl.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        return (ResponseEntity<User>) ResponseEntity.accepted();
    }
}
