package com.wroclawroutes.routes.component.impl;

import com.wroclawroutes.routes.component.model.TSPInputData;
import com.wroclawroutes.routes.component.model.TSPOutputData;
import com.wroclawroutes.routes.dto.OptimizedStepsRequest;
import com.wroclawroutes.routes.dto.OptimizedStepsResponse;
import com.wroclawroutes.routes.dto.RouteStepResponse;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.service.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TSPDataMapper {
    private final LocationMapper locationMapper;
    public TSPInputData map(OptimizedStepsRequest optimizedStepsRequest, Map<Integer, Location> indexedLocations){
        final Location depot = optimizedStepsRequest.getDepot();

        long[][] distanceMatrix = getDistanceMatrix(indexedLocations, optimizedStepsRequest);
        final int indexOfDepot = getIndexOfDepot(indexedLocations, depot);

        return TSPInputData.builder().distanceMatrix(distanceMatrix).indexOfDepot(indexOfDepot).build();
    }

    public OptimizedStepsResponse map(TSPOutputData outputData, Map<Integer, Location> indexedLocations){
        final List<RouteStepResponse> optimizedSteps = IntStream.range(0, outputData.getOrderedIndexes().size())
                .boxed()
                .map(s-> RouteStepResponse
                                .builder()
                                .location(locationMapper.map(indexedLocations.get(outputData.getOrderedIndexes().get(s))))
                                .step(s)
                                .build())
                .toList();

        return OptimizedStepsResponse
                .builder()
                .value(outputData.getValue())
                .optimizedSteps(optimizedSteps)
                .build();
    }

    private static int getIndexOfDepot(Map<Integer, Location> indexedLocations, Location depot) {
        return indexedLocations
                .entrySet()
                .stream()
                .filter(s->s.getValue().equals(depot))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Missing depot location in indexed locations"));
    }

    private long[][] getDistanceMatrix(Map<Integer, Location> indexedLocations, OptimizedStepsRequest optimizedStepsRequest) {
        final int row = indexedLocations.size();
        long[][] distanceMatrix = new long[row][row];
        for(int i = 0; i<row; i++){
            for (int j = 0; j<row; j++){
                final int indexOfStartLocation = i;
                final int indexOfEndLocation = j;
                distanceMatrix[i][j] = optimizedStepsRequest.getLocationConnections()
                        .stream()
                        .filter(s->s.getStartLocation().equals(indexedLocations.get(indexOfStartLocation)))
                        .filter(s->s.getEndLocation().equals(indexedLocations.get(Integer.valueOf(indexOfEndLocation))))
                        .map(LocationConnection::getTimeOnFootInSec)
                        .findFirst()
                        .orElse(0);
            }
        }
        return distanceMatrix;
    }

}
