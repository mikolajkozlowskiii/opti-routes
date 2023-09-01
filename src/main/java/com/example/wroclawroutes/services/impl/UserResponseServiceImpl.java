package com.example.wroclawroutes.services.impl;

import com.example.wroclawroutes.dto.ApiResponse;
import com.example.wroclawroutes.dto.UserRequest;
import com.example.wroclawroutes.dto.UserResponse;
import com.example.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.example.wroclawroutes.services.UserResponseService;
import com.example.wroclawroutes.services.UserService;
import com.example.wroclawroutes.services.components.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserResponseServiceImpl implements UserResponseService {
    private final UserService userService;
    private final UserMapper userMapper;
    @Override
    public UserResponse findUsersResponseByEmail(String email) {
        return userMapper.map(userService.findUserByEmail(email));
    }

    @Override
    public UserResponse findUsersResponseById(Long id) {
        return userMapper.map(userService.findUserById(id));
    }

    @Override
    public UserResponse getCurrentUserResponse(UserDetailsImpl userDetails) {
        return userMapper.map(userService.getCurrentUser(userDetails));
    }

    @Override
    public UserResponse updateUserResponse(UserRequest updatedUser, String email, UserDetailsImpl currentUser) {
        return userMapper.map(userService.updateUser(updatedUser, email, currentUser));
    }

    @Override
    public ApiResponse deleteUser(String email, UserDetailsImpl currentUser) {
        userService.deleteUser(email, currentUser);
        return new ApiResponse(Boolean.TRUE, "User " + email + " has been deleted.");
    }

    @Override
    public ApiResponse addModeratorRole(String email) {
        userService.addModeratorRole(email);
        return new ApiResponse(Boolean.TRUE, "Moderator role set to user: " + email);
    }

    @Override
    public ApiResponse removeModeratorRole(String email) {
        userService.removeModeratorRole(email);
        return new ApiResponse(Boolean.TRUE, "Moderator role removed from user: " + email);
    }

    @Override
    public List<UserResponse> findAllUsersResponse() {
        return userService
                .findAllUsers()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    @Override
    public List<UserResponse> findAllUsersResponseWithOnlyUserRole() {
        return userService
                .findAllUsersWithOnlyUserRole()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    @Override
    public List<UserResponse> findAllUsersResponseWithOnlyModeratorRole() {
        return userService
                .findAllUsersWithOnlyModeratorRole()
                .stream()
                .map(userMapper::map)
                .toList();
    }
}
