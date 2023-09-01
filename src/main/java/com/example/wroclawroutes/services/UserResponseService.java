package com.example.wroclawroutes.services;

import com.example.wroclawroutes.dto.ApiResponse;
import com.example.wroclawroutes.dto.UserRequest;
import com.example.wroclawroutes.dto.UserResponse;
import com.example.wroclawroutes.entities.User;
import com.example.wroclawroutes.security.userdetails.UserDetailsImpl;

import java.util.List;

public interface UserResponseService {
    UserResponse findUsersResponseByEmail(String email);
    UserResponse findUsersResponseById(Long id);
    UserResponse getCurrentUserResponse(UserDetailsImpl userDetails);
    UserResponse updateUserResponse(UserRequest updatedUser, String username, UserDetailsImpl currentUser);
    ApiResponse deleteUser(String email, UserDetailsImpl currentUser);
    ApiResponse addModeratorRole(String email);
    ApiResponse removeModeratorRole(String email);
    List<UserResponse> findAllUsersResponse();
    List<UserResponse> findAllUsersResponseWithOnlyUserRole();
    List<UserResponse> findAllUsersResponseWithOnlyModeratorRole();
}
