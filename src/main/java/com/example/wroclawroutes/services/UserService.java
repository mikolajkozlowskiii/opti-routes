package com.example.wroclawroutes.services;

import com.example.wroclawroutes.dto.SignUpRequest;
import com.example.wroclawroutes.dto.UserRequest;
import com.example.wroclawroutes.entities.User;
import com.example.wroclawroutes.security.userdetails.UserDetailsImpl;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    User findUserById(Long id);
    List<User> findAllUsers();
    List<User> findAllUsersWithOnlyUserRole();
    List<User> findAllUsersWithOnlyModeratorRole();
    User createLocalUser(SignUpRequest request);
    User getCurrentUser(UserDetailsImpl userDetails);
    User updateUser(UserRequest updatedUser, String username, UserDetailsImpl currentUser);
    boolean checkEmailAvailability(String email);
    int enableUser(String email);
    void deleteUser(String email, UserDetailsImpl currentUser);
    void addModeratorRole(String email);
    void removeModeratorRole(String email);
}
