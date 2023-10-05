package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findById(Long id);
    @Query("SELECT l from Location l LEFT JOIN FETCH l.outgoingConnections WHERE l.id=:id")
    Optional<Location> findByIdAndFetchOutgoingConnectionEagerly(@Param("id") Long id);
    Optional<Location> findByName(String name);
    Optional<Location> findByLatitudeAndLongitude(Double latitude, Double longtitude);
    Boolean existsByLatitudeAndLongitude(Double latitude, Double longtitude);
    List<Location> findAll();
    @Query("SELECT l from Location l LEFT JOIN FETCH l.outgoingConnections")
    List<Location> findAllFetchOutogingConnectionEagerly();
    @Query("SELECT l from Location l LEFT JOIN FETCH l.likedByUsers")
    List<Location> findAllFetchLikedByUsersLocationEagerly();
}
