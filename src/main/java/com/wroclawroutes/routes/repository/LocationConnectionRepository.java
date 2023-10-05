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
    List<LocationConnection> findAllByStartLocation(Location startLocation);
    List<LocationConnection> findAllByStartLocationAndEndLocation(Location startLocation, Location endLocation);
}
