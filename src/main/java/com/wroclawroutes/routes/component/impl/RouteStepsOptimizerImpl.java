package com.wroclawroutes.routes.component.impl;

import com.wroclawroutes.routes.component.RouteStepsOptimizer;
import com.wroclawroutes.routes.component.TSPSolver;
import com.wroclawroutes.routes.component.model.TSPInputData;
import com.wroclawroutes.routes.component.model.TSPOutputData;
import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.entity.RouteStep;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class RouteStepsOptimizerImpl implements RouteStepsOptimizer {
    private final TSPSolver tspSolver;
    private final TSPDataMapper tspDataMapper;

    public OptimizedStepsResponse getOptimizedSteps(OptimizedStepsRequest request){
        final Set<LocationConnectionDTO> locationConnections = request.getLocationConnections();
        validateLocationConnections(locationConnections);
        final Map<Integer, LocationDTO> indexedLocations = getIndexedLocations(locationConnections);

        final TSPInputData inputTSP = tspDataMapper.map(request, indexedLocations);

        final TSPOutputData outputTSP = tspSolver.solve(inputTSP);
        return tspDataMapper.map(outputTSP, indexedLocations);
    }

    private void validateLocationConnections(Set<LocationConnectionDTO> locationConnections) {
        for(LocationConnectionDTO locationConnection : locationConnections){
            if(!locationConnections.contains(
                    LocationConnectionDTO
                            .builder()
                            .startLocation(locationConnection.getEndLocation())
                            .endLocation(locationConnection.getStartLocation())
                            .build())
            ){
                throw new IllegalArgumentException("LocationConnections doesn't make cube matrix.");
            }
        }
    }

    private Map<Integer, LocationDTO> getIndexedLocations(Set<LocationConnectionDTO> locationConnections){
        final List<LocationDTO> originDestinationLocation = locationConnections
                .stream()
                .map(LocationConnectionDTO::getStartLocation)
                .distinct()
                .toList();


        return IntStream.range(0, originDestinationLocation.size())
                .boxed()
                .collect(Collectors.toMap(i -> i, originDestinationLocation::get));
    }

}
