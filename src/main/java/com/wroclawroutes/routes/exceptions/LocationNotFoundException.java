package com.wroclawroutes.routes.exceptions;

public class LocationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public LocationNotFoundException(String name){super("Location with: " + name + " does not exists");}
    public LocationNotFoundException(Long id){super("Location with id: " + id + " does not exists");}
    public LocationNotFoundException(Double latitude, Double longtitude){
        super("Location with latitude: %s, and longtitude: %sdoes not exists".formatted(latitude, longtitude));
    }

}
