package com.wroclawroutes.security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JwtResponse {
    private String token;
    private static final String TYPE = "Bearer";
    private Long userId;
    private String email;
    private Set<String> roles;
}
