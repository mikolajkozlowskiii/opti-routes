package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationConnectionRepository extends JpaRepository<LocationConnection, Long> {
    Optional<LocationConnection> findById(Long id);
    Optional<LocationConnection> findByStartLocationAndEndLocation(Location startLocation, Location endLocation);
    Optional<LocationConnection> findByStartLocation_LatitudeAndStartLocation_LongitudeAndEndLocation_LatitudeAndEndLocation_Longitude(
            Double startLocationLatitude, Double startLocationLongitute,
            Double endLocationLatitude, Double endLocationLongitute
    );
    List<LocationConnection> findAllByStartLocation(Location startLocation);
}
