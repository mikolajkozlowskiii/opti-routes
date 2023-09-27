package com.wroclawroutes.users.services;

import com.wroclawroutes.security.dto.SignUpRequest;
import com.wroclawroutes.users.dto.UserRequest;
import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;

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
