package com.example.wroclawroutes.services;

import com.example.wroclawroutes.dto.UserResponse;
import com.example.wroclawroutes.entities.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    User findUserById(Long id);
    List<User> findAllUsers();
    List<User> findAllUsersWithOnlyUserRole();
    List<User> findAllUsersWithOnlyModeratorRole();
}
