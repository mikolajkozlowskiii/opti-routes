package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;

import java.util.List;
import java.util.Set;

public interface LocationConnectionService {
    LocationConnection findById(Long id);
    List<LocationConnection> findAllByStartLocation(Location startLocation);
    Set<LocationConnection> findAllConnectionsBetweenLocations(Set<Location> locations);
    LocationConnection findByStartLocationAndEndLocation(Location startLocation, Location endLocation);
}
