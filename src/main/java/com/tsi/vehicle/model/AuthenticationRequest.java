package com.tsi.vehicle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticationRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String confirmPassword;
    private String email;

}
