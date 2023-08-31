package com.example.wroclawroutes.exceptions;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public UserNotFoundException(String email){super("User with email: " + email + " does not exists");}
    public UserNotFoundException(Long id){super("User with id: " + id + " does not exists");}
}
