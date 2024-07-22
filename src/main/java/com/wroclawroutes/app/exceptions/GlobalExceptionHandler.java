package com.wroclawroutes.app.exceptions;


import com.wroclawroutes.routes.exceptions.RouteNotFoundException;
import com.wroclawroutes.users.exceptions.RoleNotFoundException;
import com.wroclawroutes.users.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler({
            RoleNotFoundException.class,
            UserNotFoundException.class,
            RouteNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseStatusException hadnleNotFound(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ResponseBody
    @ExceptionHandler({
           ApiException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseStatusException handleConflict(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
    }

    @ResponseBody
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}




