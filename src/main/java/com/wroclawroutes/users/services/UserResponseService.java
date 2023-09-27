package com.wroclawroutes.users.services;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.users.dto.UserRequest;
import com.wroclawroutes.users.dto.UserResponse;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;

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
