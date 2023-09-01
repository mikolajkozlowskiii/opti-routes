package com.example.wroclawroutes.services;

import com.example.wroclawroutes.dto.JwtResponse;
import com.example.wroclawroutes.dto.LoginRequest;

public interface AuthService {
    JwtResponse signIn(LoginRequest request);
}
