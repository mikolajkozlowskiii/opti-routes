package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.service.implementation.RouteOptimizationServiceImpl;
import com.wroclawroutes.routes.util.TSPInputDataR;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class RouteOptimizationServiceImplTest {
    @InjectMocks
    private RouteOptimizationServiceImpl routeOptimizationService;
//    @Test
//    void getOptimizedRoute() {
//
//        final Location location1 = Location
//                .builder()
//                .name("Sky Tower")
//                .build();
//
//        final Location location2 = Location
//                .builder()
//                .name("Hydropolis")
//                .build();
//
//        final Location location3 = Location
//                .builder()
//                .name("Arkady Capitol")
//                .build();
//
//        final long[][] distanceMatrix = {
//                {0L, 12L, 3L},
//                {3L, 0L, 3L},
//                {3L, 4L, 0L}
//        };
//        final TSPInputDataR data = TSPInputDataR
//                .builder()
//                .locations(Map.of(0, location1, 1, location2, 2, location3))
//                .depot(0)
//                .distanceMatrix(distanceMatrix)
//                .build();
//
//        final Route route = routeOptimizationService.getOptimizedRoute(data);
//        System.out.println(route.getDistanceInMeters());
//        route.getSteps().forEach(s-> System.out.println(s.getLocation().getName()+" , step: "+s.getStep()));
//    }
}

// 1 2 3 1   2+3+3
// 1 3 2 1   3+4+3