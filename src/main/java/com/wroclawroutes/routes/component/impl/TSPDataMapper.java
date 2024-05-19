package com.wroclawroutes.routes.component.impl;

import com.wroclawroutes.routes.component.model.TSPInputData;
import com.wroclawroutes.routes.component.model.TSPOutputData;
import com.wroclawroutes.routes.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TSPDataMapper {
    public TSPInputData map(OptimizedStepsRequest optimizedStepsRequest, Map<Integer, LocationDTO> indexedLocations){
        final LocationDTO depot = optimizedStepsRequest.getDepot();

        long[][] distanceMatrix = getDistanceMatrix(indexedLocations, optimizedStepsRequest);
        final int indexOfDepot = getIndexOfDepot(indexedLocations, depot);

        return TSPInputData
                .builder()
                .distanceMatrix(distanceMatrix)
                .indexOfDepot(indexOfDepot)
                .limitImprovingHeuristicInSeconds(optimizedStepsRequest.getLimitImprovingHeuristicInSeconds())
                .build();
    }

    public OptimizedStepsResponse map(TSPOutputData outputData, Map<Integer, LocationDTO> indexedLocations){
        final List<RouteStepDTO> optimizedSteps = IntStream.range(0, outputData.getOrderedIndexes().size())
                .boxed()
                .map(s-> RouteStepDTO
                                .builder()
                                .location(indexedLocations.get(outputData.getOrderedIndexes().get(s)))
                                .step(s)
                                .build())
                .toList();

        return OptimizedStepsResponse
                .builder()
                .totalTimeInSeconds(outputData.getValue())
                .optimizedSteps(optimizedSteps)
                .build();
    }

    private static int getIndexOfDepot(Map<Integer, LocationDTO> indexedLocations, LocationDTO depot) {
        return indexedLocations
                .entrySet()
                .stream()
                .filter(s->s.getValue().equals(depot))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Missing depot location in indexed locations"));
    }

    private long[][] getDistanceMatrix(Map<Integer, LocationDTO> indexedLocations, OptimizedStepsRequest optimizedStepsRequest) {
        final int row = indexedLocations.size();
        long[][] distanceMatrix = new long[row][row];
        for(int i = 0; i<row; i++){
            for (int j = 0; j<row; j++){
                int indexOfStartLocation = i;
                int indexOfEndLocation = j;
                distanceMatrix[i][j] = optimizedStepsRequest.getLocationConnections()
                        .stream()
                        .filter(s->s.getStartLocation().equals(indexedLocations.get(indexOfStartLocation)))
                        .filter(s->s.getEndLocation().equals(indexedLocations.get(indexOfEndLocation)))
                        .map(LocationConnectionDTO::getTimeInMiliSeconds)
                        .findFirst()
                        .orElse(0);
            }
        }
        return distanceMatrix;
    }

}
