package com.wroclawroutes.security.services.components;

import com.wroclawroutes.security.dto.JwtResponse;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JwtMapper {
    public JwtResponse map(String tokenJwt, UserDetailsImpl userDetails){
        return JwtResponse.builder()
                .token(tokenJwt)
                .userId(userDetails.getId())
                .email(userDetails.getEmail())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()))
                .build();
    }
}
