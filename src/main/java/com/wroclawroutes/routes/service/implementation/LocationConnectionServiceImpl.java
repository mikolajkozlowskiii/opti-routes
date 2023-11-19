package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.repository.LocationConnectionRepository;
import com.wroclawroutes.routes.service.LocationConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LocationConnectionServiceImpl implements LocationConnectionService {
    private final LocationConnectionRepository locationConnectionRepository;
    @Override
    public LocationConnection findById(Long id) {
        return locationConnectionRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("LocationConnection with id: %d not found.".formatted(id)));
    }

    @Override
    public List<LocationConnection> findAllByStartLocation(Location startLocation) {
        return locationConnectionRepository.findAllByStartLocation(startLocation);
    }

    @Override
    public Set<LocationConnection> findAllConnectionsBetweenLocations(Set<Location> locations) {
        // TODO
        return null;
    }

    @Override
    public LocationConnection findByStartLocationAndEndLocation(Location startLocation, Location endLocation) {
        return locationConnectionRepository
                .findByStartLocationAndEndLocation(startLocation, endLocation)
                .orElseThrow(() -> new NoSuchElementException(("LocationConnection with startLocation id: %d, " +
                        "endLocation id: %d not found.").formatted(startLocation.getId(), endLocation.getId())));
    }
}
