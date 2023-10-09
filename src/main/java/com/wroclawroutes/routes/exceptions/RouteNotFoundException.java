package com.wroclawroutes.routes.exceptions;

public class RouteNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public RouteNotFoundException(Long id){super("Route with id: " + id + " does not exists");}
}
