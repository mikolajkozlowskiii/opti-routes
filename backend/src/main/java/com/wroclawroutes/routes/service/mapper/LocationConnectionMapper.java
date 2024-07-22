package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.LocationConnectionDTO;
import com.wroclawroutes.routes.entity.LocationConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationConnectionMapper {
    private final LocationMapper locationMapper;
    public LocationConnectionDTO map(LocationConnection locationConnection){
        return LocationConnectionDTO.builder()
                .startLocation(locationMapper.map(locationConnection.getStartLocation()))
                .endLocation(locationMapper.map(locationConnection.getEndLocation()))
                .timeInMiliSeconds(locationConnection.getTimeOnFootInSec())
                .distanceInMeters(locationConnection.getDistanceInMeters())
                .build();
    }
}
