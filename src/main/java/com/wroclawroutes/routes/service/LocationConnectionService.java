package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;

import java.util.List;

public interface LocationConnectionService {
    LocationConnection findById(Long id);
    List<LocationConnection> findAllByStartLocation(Location startLocation);
    LocationConnection findByStartLocationAndEndLocation(Location startLocation, Location endLocation);
}
