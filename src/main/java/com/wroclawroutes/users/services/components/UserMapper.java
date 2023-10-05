package com.wroclawroutes.users.services.components;

import com.wroclawroutes.users.dto.UserResponse;
import com.wroclawroutes.users.entities.Role;
import com.wroclawroutes.users.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponse map(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }
}
