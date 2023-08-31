package com.example.wroclawroutes.services;

import com.example.wroclawroutes.dto.UserResponse;

import java.util.List;

public interface UserResponseService {
    UserResponse findUsersResponseByEmail(String email);
    UserResponse findUsersResponseById(Long id);
    List<UserResponse> findAllUsersResponse();
    List<UserResponse> findAllUsersResponseWithOnlyUserRole();
    List<UserResponse> findAllUsersResponseWithOnlyModeratorRole();
}
