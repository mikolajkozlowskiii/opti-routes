package com.example.wroclawroutes.services.impl;

import com.example.wroclawroutes.dto.UserResponse;
import com.example.wroclawroutes.services.UserResponseService;
import com.example.wroclawroutes.services.UserService;
import com.example.wroclawroutes.services.mappers.UserMapper;
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
