package com.tsi.vehicle.services.impl;

import com.tsi.vehicle.model.AuthenticationRequest;
import com.tsi.vehicle.model.User;
import com.tsi.vehicle.repository.UserRepository;
import com.tsi.vehicle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
//        String randomId = UUID.randomUUID().toString();
//        user.setId(Long.valueOf(randomId));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getDetails(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> loadUserByUserName(String username) {
        return Optional.empty();
    }

    @Override
    public Boolean userExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public User buildUser(AuthenticationRequest authenticationRequest) {
        return User.builder()
                .username(authenticationRequest.getUsername())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .email(authenticationRequest.getEmail())
                .firstname(authenticationRequest.getFirstname())
                .lastname(authenticationRequest.getLastname())
                .build();
    }


}
