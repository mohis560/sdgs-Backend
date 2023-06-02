package com.tsi.vehicle.services;

import com.tsi.vehicle.model.AuthenticationRequest;
import com.tsi.vehicle.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    public Optional<User> getDetails(Long id);

    public void deleteUserById(Long id);

    Optional<User> loadUserByUserName(String username);

    User buildUser(AuthenticationRequest authenticationRequest);

    Boolean userExists(String username);

}
