package com.wroclawroutes.users.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String password;
    private String firstName;
    private String lastName;
}

