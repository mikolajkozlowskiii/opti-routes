package com.example.wroclawroutes.services.impl;

import com.example.wroclawroutes.dto.JwtResponse;
import com.example.wroclawroutes.dto.LoginRequest;
import com.example.wroclawroutes.security.jwt.JwtUtils;
import com.example.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.example.wroclawroutes.services.AuthService;
import com.example.wroclawroutes.services.components.JwtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JwtMapper jwtMapper;
    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtMapper.map(jwt, userDetails);
    }
}
