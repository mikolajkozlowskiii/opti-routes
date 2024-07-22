package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.LocationDTO;
import com.wroclawroutes.routes.dto.LocationResponse;
import com.wroclawroutes.routes.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDTO map(Location location) {
        return LocationDTO
                .builder()
                .name(location.getName())
                .address(location.getAddress())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build();
    }

    public Location map(LocationDTO locationDTO){
        return Location
                .builder()
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .name(locationDTO.getName())
                .address(locationDTO.getAddress())
                .build();
    }
}