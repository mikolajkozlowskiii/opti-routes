package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.LocationDTO;
import com.wroclawroutes.routes.dto.LocationResponse;
import com.wroclawroutes.routes.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDTO map(Location location){
        return LocationDTO
                .builder()
                .name(location.getName())
                .address(location.getAddress())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build();
    }
//    public LocationResponse map(Location location){
//        return LocationResponse
//                .builder()
//                .name(location.getName())
//                .address(location.getAddress())
//                .latitude(location.getLatitude())
//                .longitude(location.getLongitude())
//                .build();
//    }
}
