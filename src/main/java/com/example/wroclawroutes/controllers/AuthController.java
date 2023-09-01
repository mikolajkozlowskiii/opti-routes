package com.example.wroclawroutes.controllers;

import com.example.wroclawroutes.dto.ApiResponse;
import com.example.wroclawroutes.dto.JwtResponse;
import com.example.wroclawroutes.dto.LoginRequest;
import com.example.wroclawroutes.dto.SignUpRequest;
import com.example.wroclawroutes.entities.User;
import com.example.wroclawroutes.services.AuthService;
import com.example.wroclawroutes.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        final JwtResponse jwtResponse = authService.signIn(loginRequest);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (!userService.checkEmailAvailability(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(Boolean.FALSE, "Error: Email is already in use!"));
        }

        final User createdUser = userService.createLocalUser(signUpRequest);

        // confirmationTokenService.sendConfirmationEmail(createdUser);

        final URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users/{email}")
                .buildAndExpand(createdUser.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "Account registered," +
                " to enable your account confirm your email in 15 min."));
    }



}
