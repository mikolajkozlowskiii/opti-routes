package com.wroclawroutes.users.controllers;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.users.dto.UserRequest;
import com.wroclawroutes.users.dto.UserResponse;
import com.wroclawroutes.users.entities.ERole;
import com.wroclawroutes.security.userdetails.CurrentUser;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.services.RoleService;
import com.wroclawroutes.users.services.UserResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserResponseService userResponseService;
    private final RoleService roleService;

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
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserDetailsImpl currentUser){
        final UserResponse userResponse = userResponseService.getCurrentUserResponse(currentUser);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getCurrentUser(@PathVariable(value = "email") String email){
        return new ResponseEntity<>(userResponseService.findUsersResponseByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "email") String email,
                                        @Valid @RequestBody UserRequest updateUserRequest,
                                        @CurrentUser UserDetailsImpl currentUser){
        if (!Objects.equals(currentUser.getEmail(), email)
                && !currentUser.getAuthorities().contains(roleService.getRole(ERole.ROLE_ADMIN))) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(Boolean.FALSE, "Can't update not your account!"));
        }

        final UserResponse userResponse = userResponseService.updateUserResponse(updateUserRequest, email, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/users/{email}")
                .buildAndExpand(userResponse.getEmail())
                .toUri();

        return ResponseEntity.created(location).body(userResponse);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "email") String email,
                                                  @CurrentUser UserDetailsImpl currentUser){
        return ResponseEntity.ok(userResponseService.deleteUser(email, currentUser));
    }

    @PutMapping("/{email}/mod")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> giveMod(@PathVariable(value = "email") String email){
        return ResponseEntity.ok(userResponseService.addModeratorRole(email));
    }

    @DeleteMapping("/{email}/mod")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeMode(@PathVariable(value = "email") String email){
        return ResponseEntity.ok(userResponseService.removeModeratorRole(email));
    }
}
