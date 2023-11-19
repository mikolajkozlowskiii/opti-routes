package com.wroclawroutes.routes.component.impl;

import com.wroclawroutes.routes.component.RouteStepsOptimizer;
import com.wroclawroutes.routes.component.TSPSolver;
import com.wroclawroutes.routes.component.model.TSPInputData;
import com.wroclawroutes.routes.component.model.TSPOutputData;
import com.wroclawroutes.routes.dto.OptimizedStepsRequest;
import com.wroclawroutes.routes.dto.OptimizedStepsResponse;
import com.wroclawroutes.routes.dto.RouteStepResponse;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.entity.RouteStep;
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
        final Map<Integer, Location> indexedLocations = getIndexedLocations(request.getLocationConnections());
        final TSPInputData inputTSP = tspDataMapper.map(request, indexedLocations);
        final TSPOutputData outputTSP = tspSolver.solve(inputTSP);
        return tspDataMapper.map(outputTSP, indexedLocations);
    }

    private Map<Integer, Location> getIndexedLocations(Set<LocationConnection> locationConnections){
        final List<Location> originDestinationLocation = locationConnections
                .stream()
                .map(LocationConnection::getStartLocation)
                .distinct()
                .toList();

        return IntStream.range(0, originDestinationLocation.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, originDestinationLocation::get));
    }

}
