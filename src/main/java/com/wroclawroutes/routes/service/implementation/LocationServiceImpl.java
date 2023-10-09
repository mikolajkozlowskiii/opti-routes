package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.exceptions.LocationNotFoundException;
import com.wroclawroutes.routes.repository.LocationRepository;
import com.wroclawroutes.routes.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void delete(Location location) {
        locationRepository.delete(location);
    }

    @Override
    public Location findById(Long id) {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    @Override
    public Location findByName(String name) {
        return locationRepository
                .findByName(name)
                .orElseThrow(() -> new LocationNotFoundException(name));
    }

    @Override
    public Location findByLatitudeAndLongitude(Double latitude, Double longtitude) {
        return locationRepository
                .findByLatitudeAndLongitude(latitude, longtitude)
                .orElseThrow(() -> new LocationNotFoundException(latitude, longtitude));
    }

    @Override
    public Boolean existsByLatitudeAndLongitude(Double latitude, Double longtitude) {
        return locationRepository.existsByLatitudeAndLongitude(latitude, longtitude);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> findAllFetchOutogingConnectionEagerly() {
        return locationRepository.findAllFetchOutogingConnectionEagerly();
    }

    @Override
    public List<Location> findAllFetchLikedByUsersLocationEagerly() {
        return locationRepository.findAllFetchLikedByUsersLocationEagerly();
    }
}
