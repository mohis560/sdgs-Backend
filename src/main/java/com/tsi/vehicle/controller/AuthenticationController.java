package com.tsi.vehicle.controller;

import com.tsi.vehicle.jwt.JwtTokenProvider;
import com.tsi.vehicle.model.AuthenticationRequest;
import com.tsi.vehicle.model.AuthenticationResponse;
import com.tsi.vehicle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getUsername(),
                                authenticationRequest.getPassword()));
        String token = jwtTokenProvider.createToken(authenticate);
        return ResponseEntity.status(HttpStatus.OK).body(AuthenticationResponse.builder()
                .username(authenticationRequest.getUsername()).token(token).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody AuthenticationRequest authenticationRequest) {
        if (Objects.isNull(authenticationRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (Objects.nonNull(authenticationRequest) &&
                !authenticationRequest.getPassword().equals(authenticationRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (userService.userExists(authenticationRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.saveUser(userService.buildUser(authenticationRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
