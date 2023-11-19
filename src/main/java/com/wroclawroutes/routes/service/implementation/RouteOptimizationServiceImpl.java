package com.wroclawroutes.routes.service.implementation;

import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.wroclawroutes.routes.component.RouteStepsOptimizer;
import com.wroclawroutes.routes.component.TSPSolver;
import com.wroclawroutes.routes.dto.OptimizedStepsRequest;
import com.wroclawroutes.routes.dto.OptimizedStepsResponse;
import com.wroclawroutes.routes.dto.ProposedRouteReponse;
import com.wroclawroutes.routes.dto.RouteResponse;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import com.wroclawroutes.routes.service.LocationConnectionService;
import com.wroclawroutes.routes.service.LocationService;
import com.wroclawroutes.routes.service.RouteOptimizationService;
import com.wroclawroutes.routes.component.model.TSPOutputData;
import com.wroclawroutes.routes.service.mapper.RouteMapper;
import com.wroclawroutes.routes.util.TSPInputDataR;
import com.wroclawroutes.routes.component.model.TSPInputData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteOptimizationServiceImpl implements RouteOptimizationService {
    private final LocationConnectionService locationConnectionService;
    private final LocationService locationService;
    private final RouteStepsOptimizer routeStepsOptimizer;
    private final RouteMapper routeMapper;
    //    @Override
//    public Route getOptimizedRoute(TSPInputDataR data) {
//        Loader.loadNativeLibraries();
//
//        final long[][] distanceMatrix = data.getDistanceMatrix();
//        RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, 1, data.getDepot());
//        RoutingModel routing = new RoutingModel(manager);
//        final int transitCallbackIndex =
//                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
//                    // Convert from routing variable Index to user NodeIndex.
//                    int fromNode = manager.indexToNode(fromIndex);
//                    int toNode = manager.indexToNode(toIndex);
//                    return distanceMatrix[fromNode][toNode];
//                });
//
//        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);
//
//        RoutingSearchParameters searchParameters =
//                main.defaultRoutingSearchParameters()
//                        .toBuilder()
//                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
//                        //.setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
//                        //.setTimeLimit(Duration.newBuilder().setSeconds(30).build())
//                        //.setLogSearch(true)
//                        .build();
//        Assignment solution = routing.solveWithParameters(searchParameters);
//
//        Set<RouteStep> steps = new HashSet<>();
//        int routeDistance = 0;
//        long index = routing.start(0);
//        int step = 0;
//        String route = "";
//        while (!routing.isEnd(index)) {
//            route += manager.indexToNode(index) + " -> ";
//            step++;
//            steps.add(RouteStep.builder().step(step).location(data.getLocation(((int) index))).build());
//            long previousIndex = index;
//            index = solution.value(routing.nextVar(index));
//            routeDistance += (int) routing.getArcCostForVehicle(previousIndex, index, 0);
//        }
//        System.out.println(steps.size());
//        System.out.println(route);
//        System.out.println(solution.objectiveValue());
//        return Route.builder().steps(steps).distanceInMeters(routeDistance).build();
//    }

    @Override
    public OptimizedStepsResponse getOptimizedRoute(Set<Location> locations, Location depot) {
        final Set<LocationConnection> locationConnections =
                locationConnectionService.findAllConnectionsBetweenLocations(locations);

        final OptimizedStepsRequest request = OptimizedStepsRequest
                .builder()
                .locationConnections(locationConnections)
                .depot(depot)
                .build();

        return routeStepsOptimizer.getOptimizedSteps(request);
    }

    @Override
    public OptimizedStepsResponse getOptimizedRoute(Set<Long> locationsId, Long depotId) {
        final Location depot = locationService.findById(depotId);
        final Set<Location> locations = locationsId
                .stream()
                .map(locationService::findById)
                .collect(Collectors.toSet());
        return getOptimizedRoute(locations, depot);
    }

}
