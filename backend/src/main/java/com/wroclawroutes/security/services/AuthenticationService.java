package com.wroclawroutes.security.services;

import com.wroclawroutes.security.dto.JwtResponse;
import com.wroclawroutes.security.dto.LoginRequest;

public interface AuthenticationService {
    JwtResponse signIn(LoginRequest request);
}
