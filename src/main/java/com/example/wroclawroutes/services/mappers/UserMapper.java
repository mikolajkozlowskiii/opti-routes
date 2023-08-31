package com.example.wroclawroutes.services.mappers;

import com.example.wroclawroutes.dto.UserResponse;
import com.example.wroclawroutes.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse map(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
