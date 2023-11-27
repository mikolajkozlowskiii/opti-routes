package com.wroclawroutes.routes.component.impl;

import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.google.protobuf.Duration;
import com.wroclawroutes.routes.component.TSPSolver;
import com.wroclawroutes.routes.component.model.TSPOutputData;
import com.wroclawroutes.routes.component.model.TSPInputData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TSPSolverGoogleORTools implements TSPSolver {
    @Override
    public TSPOutputData solve(TSPInputData data){
        Loader.loadNativeLibraries();

        final long[][] distanceMatrix = data.getDistanceMatrix();
        final int indexOfDepot = data.getIndexOfDepot();
        final RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, 1, indexOfDepot);
        final RoutingModel routing = new RoutingModel(manager);
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return distanceMatrix[fromNode][toNode];
                });

        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        final RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.SIMULATED_ANNEALING)
                        .setTimeLimit(Duration.newBuilder().setSeconds(30).build())
                        .setLogSearch(true)
                        .build();
        final Assignment solution = routing.solveWithParameters(searchParameters);

        final List<Integer> orderedIndexes = new ArrayList<>();
        long index = routing.start(0);
        while (!routing.isEnd(index)) {
            orderedIndexes.add(manager.indexToNode(index));
            index = solution.value(routing.nextVar(index));
        }
        orderedIndexes.add(manager.indexToNode(index));
        return TSPOutputData
                .builder()
                .value(solution.objectiveValue())
                .orderedIndexes(orderedIndexes)
                .build();
    }
}
