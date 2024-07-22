package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.LocationStatResponse;
import com.wroclawroutes.routes.dto.RouteRequest;
import com.wroclawroutes.routes.dto.RouteStepDTO;
import com.wroclawroutes.routes.dto.RouteStepStatsResponse;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteStepMapper {
    private final LocationMapper locationMapper;

    public LocationStatResponse mapToLocationStatResponse(RouteStep routeStep, int numOfLikes){
        return LocationStatResponse
                .builder()
                .id(routeStep.getLocation().getId())
                .locationInfo(locationMapper.map(routeStep.getLocation()))
                .numOfLikes(numOfLikes)
                .build();
    }
    public RouteStepDTO map(RouteStep routeStep){
        return RouteStepDTO
                .builder()
                .step(routeStep.getStep())
                .location(locationMapper.map(routeStep.getLocation()))
                .build();
    }

    public RouteStep map(RouteStepDTO routeStepDTO){
        return RouteStep
                .builder()
                .location(locationMapper.map(routeStepDTO.getLocation()))
                .step(routeStepDTO.getStep())
                .build();
    }

//    public RouteStepStatsResponse mapWithStats(RouteStep routeStep){
//        return RouteStepStatsResponse
//                .builder()
//                .step(routeStep.getStep())
//                .
//                .location()
//                .build();
//    }
}
