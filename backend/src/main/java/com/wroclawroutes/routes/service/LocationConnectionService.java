package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.dto.OptimizedStepsResponse;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;

import java.util.List;
import java.util.Set;

public interface LocationConnectionService {
    LocationConnection findById(Long id);
    List<LocationConnection> findAllByStartLocation(Location startLocation);
    Set<LocationConnection> findAllConnectionsBetweenLocations(Set<Location> locations);
    LocationConnection findByStartLocationAndEndLocation(Location startLocation, Location endLocation);
    LocationConnection findByStartLocation_LatitudeAndStartLocation_LongitudeAndEndLocation_LatitudeAndEndLocation_Longitude(
            Double startLocationLatitude, Double startLocationLongitute,
            Double endLocationLatitude, Double endLocationLongitute
    );
}
