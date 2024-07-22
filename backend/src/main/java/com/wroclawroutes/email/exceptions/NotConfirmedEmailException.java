package com.wroclawroutes.email.exceptions;

public class NotConfirmedEmailException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NotConfirmedEmailException(String email){super("User with email: " + email + " hasn't been confirmed!");}
}
