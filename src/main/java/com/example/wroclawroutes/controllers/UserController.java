package com.example.wroclawroutes.controllers;

import com.example.wroclawroutes.dto.UserRequest;
import com.example.wroclawroutes.dto.UserResponse;
import com.example.wroclawroutes.services.UserResponseService;
import com.example.wroclawroutes.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserResponseService userResponseService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers(){
        return ResponseEntity.ok(userResponseService.findAllUsersResponse());
    }
    @GetMapping("/user-roles")
    public ResponseEntity<List<UserResponse>> findAllUserWithUserRole(){
        return ResponseEntity.ok(userResponseService.findAllUsersResponseWithOnlyUserRole());
    }

    @GetMapping("/mod-roles")
    public ResponseEntity<List<UserResponse>> findAllUserWithModRole(){
        return ResponseEntity.ok(userResponseService.findAllUsersResponseWithOnlyModeratorRole());
    }

    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity<UserResponse> getCurrentUser(){
        // TODO implementation
        return null;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getCurrentUser(@PathVariable(value = "email") String email){
        return new ResponseEntity<>(userResponseService.findUsersResponseByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "email") String email,
                                        @Valid @RequestBody UserRequest updateUserRequest){
        // TODO implementation
        return null;
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email){
       // TODO implementation
        return null;
    }

    @PutMapping("/{email}/mod")
    public ResponseEntity<?> giveMod(@PathVariable(value = "email") String email){
      // TODO implemenation
        return null;
    }

    @DeleteMapping("/{email}/mod")
    public ResponseEntity<?> removeMode(@PathVariable(value = "email") String email){
        // TODO implemenation
        return null;
    }
}
