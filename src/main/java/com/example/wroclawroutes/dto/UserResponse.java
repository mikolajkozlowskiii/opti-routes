package com.example.wroclawroutes.dto;

import com.example.wroclawroutes.entities.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Set;


@Builder
@Data
@AllArgsConstructor
@ToString
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<ERole> roles;
}

