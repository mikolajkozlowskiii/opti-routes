package com.example.wroclawroutes.services.impl;

import com.example.wroclawroutes.entities.ERole;
import com.example.wroclawroutes.entities.Role;
import com.example.wroclawroutes.entities.User;
import com.example.wroclawroutes.exceptions.UserNotFoundException;
import com.example.wroclawroutes.repositories.UserRepository;
import com.example.wroclawroutes.services.RoleService;
import com.example.wroclawroutes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllUsersWithOnlyUserRole() {
        return userRepository
                .findAll()
                .stream()
                .filter(
                        s-> !s.getRoles().contains(new Role(ERole.ROLE_MODERATOR))
                                && !s.getRoles().contains(new Role(ERole.ROLE_ADMIN)))
                .toList();
    }

    @Override
    public List<User> findAllUsersWithOnlyModeratorRole() {
        return userRepository
                .findAll()
                .stream()
                .filter(
                        s-> s.getRoles().contains(new Role(ERole.ROLE_MODERATOR))
                                && !s.getRoles().contains(new Role(ERole.ROLE_ADMIN)))
                .toList();
    }
}
