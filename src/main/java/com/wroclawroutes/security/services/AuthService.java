package com.wroclawroutes.security.services;

import com.wroclawroutes.security.dto.JwtResponse;
import com.wroclawroutes.security.dto.LoginRequest;

public interface AuthService {
    JwtResponse signIn(LoginRequest request);
}
