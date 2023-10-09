package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.entity.Location;

import java.util.List;
import java.util.Set;

public interface LocationService {
    Location save(Location location);
    void delete(Location location);
    Location findById(Long id);
    Location findByName(String name);
    Location findByLatitudeAndLongitude(Double latitude, Double longtitude);
    Boolean existsByLatitudeAndLongitude(Double latitude, Double longtitude);
    List<Location> findAll();
    List<Location> findAllFetchOutogingConnectionEagerly();
    List<Location> findAllFetchLikedByUsersLocationEagerly();
}
