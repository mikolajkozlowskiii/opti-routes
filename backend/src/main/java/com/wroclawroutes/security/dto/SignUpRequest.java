package com.wroclawroutes.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @Size(min = 3, max = 30)
    private String firstName;
    @Size(min = 3, max = 30)
    private String lastName;
}
