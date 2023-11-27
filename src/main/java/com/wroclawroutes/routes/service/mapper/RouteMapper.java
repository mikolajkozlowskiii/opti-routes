package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.OptimizedStepsResponse;
import com.wroclawroutes.routes.dto.ProposedRouteReponse;
import com.wroclawroutes.routes.entity.LocationConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RouteMapper {
    private final LocationMapper locationMapper;
    public ProposedRouteReponse map(OptimizedStepsResponse optimizedStepsResponse, Set<LocationConnection> locationConnections){
        return ProposedRouteReponse.builder()
                .distanceInMeters(optimizedStepsResponse.getTotalTimeInSeconds())
                .timeInSeconds(null) // TODO
                //.steps(optimizedStepsResponse.getOptimizedSteps())
                .build();
    }
}
