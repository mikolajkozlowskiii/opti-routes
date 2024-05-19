package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.component.RouteStepsOptimizer;
import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.exceptions.LocationNotFoundException;
import com.wroclawroutes.routes.service.LocationConnectionService;
import com.wroclawroutes.routes.service.LocationService;
import com.wroclawroutes.routes.service.RouteOptimizationService;
import com.wroclawroutes.routes.service.mapper.LocationConnectionMapper;
import com.wroclawroutes.routes.service.mapper.LocationMapper;
import com.wroclawroutes.routes.service.mapper.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteOptimizationServiceImpl implements RouteOptimizationService {
    private final LocationService locationService;
    private final LocationConnectionMapper locationConnectionMapper;
    private final LocationConnectionService locationConnectionService;
    private final RouteStepsOptimizer routeStepsOptimizer;
    private final LocationMapper locationMapper;
    private final WebClient webClient;

    @Override
    public OptimizedStepsResponse getOptimizedRoute(RouteLocationsRequest routeLocationsRequest) {
        final Set<LocationDTO> locationsRequests = routeLocationsRequest.getLocations();
        final LocationDTO depotRequest = routeLocationsRequest.getDepot();
        LocationDTO depot;

        try{
            depot = locationMapper.map(locationService.findByLatitudeAndLongitude(depotRequest.getLatitude(), depotRequest.getLongitude()));
        }catch(LocationNotFoundException ex){
            depot = LocationDTO
                    .builder()
                    .name(depotRequest.getName())
                    .address(depotRequest.getAddress())
                    .latitude(depotRequest.getLatitude())
                    .longitude(depotRequest.getLongitude())
                    .build();
        }

        final Set<LocationConnectionDTO> locationConnectionsDTOs = new HashSet<>();
        for(LocationDTO startLocationRequest : locationsRequests){
            for(LocationDTO endLocationRequest : locationsRequests){
                if(startLocationRequest.equals(endLocationRequest)){
                    final LocationConnection locationConnection = LocationConnection
                            .builder()
                            .startLocation(
                                    Location
                                            .builder()
                                            .name(startLocationRequest.getName())
                                            .address(startLocationRequest.getAddress())
                                            .latitude(startLocationRequest.getLatitude())
                                            .longitude(startLocationRequest.getLongitude())
                                            .build()
                            )
                            .endLocation(
                                    Location
                                            .builder()
                                            .name(startLocationRequest.getName())
                                            .address(startLocationRequest.getAddress())
                                            .latitude(endLocationRequest.getLatitude())
                                            .longitude(endLocationRequest.getLongitude())
                                            .build()
                            )
                            .distanceInMeters(0)
                            .timeOnFootInSec(0)
                            .build();
                    locationConnectionsDTOs.add(locationConnectionMapper.map(locationConnection));
                }
                else{
                    try{
//                        final LocationConnection locationConnection = locationConnectionService
//        .findByStartLocation_LatitudeAndStartLocation_LongitudeAndEndLocation_LatitudeAndEndLocation_Longitude(
//                                        startLocationRequest.getLatitude(), startLocationRequest.getLongitude(),
//                                        endLocationRequest.getLatitude(), endLocationRequest.getLongitude()
//                                );
//                        locationConnectionsDTOs.add(locationConnectionMapper.map(locationConnection));
                        final PathData pathData = getPathDataFromExternalAPI(startLocationRequest, endLocationRequest);
                        final LocationConnection locationConnection = LocationConnection
                                .builder()
                                .startLocation(
                                        Location
                                                .builder()
                                                .name(startLocationRequest.getName())
                                                .address(startLocationRequest.getAddress())
                                                .latitude(startLocationRequest.getLatitude())
                                                .longitude(startLocationRequest.getLongitude())
                                                .build()
                                )
                                .endLocation(
                                        Location
                                                .builder()
                                                .name(endLocationRequest.getName())
                                                .address(endLocationRequest.getAddress())
                                                .latitude(endLocationRequest.getLatitude())
                                                .longitude(endLocationRequest.getLongitude())
                                                .build()
                                )
                                .distanceInMeters(pathData.getPaths().get(0).getDistanceInMeters())
                                .timeOnFootInSec(pathData.getPaths().get(0).getTimeInMiliSeconds())
                                .build();
                        locationConnectionsDTOs.add(locationConnectionMapper.map(locationConnection));
                    } catch (NoSuchElementException ex){
                        final PathData pathData = getPathDataFromExternalAPI(startLocationRequest, endLocationRequest);
                        final LocationConnection locationConnection = LocationConnection
                                .builder()
                                .startLocation(
                                        Location
                                                .builder()
                                                .name(startLocationRequest.getName())
                                                .address(startLocationRequest.getAddress())
                                                .latitude(startLocationRequest.getLatitude())
                                                .longitude(startLocationRequest.getLongitude())
                                                .build()
                                )
                                .endLocation(
                                        Location
                                                .builder()
                                                .name(endLocationRequest.getName())
                                                .address(endLocationRequest.getAddress())
                                                .latitude(endLocationRequest.getLatitude())
                                                .longitude(endLocationRequest.getLongitude())
                                                .build()
                                )
                                .distanceInMeters(pathData.getPaths().get(0).getDistanceInMeters())
                                .timeOnFootInSec(pathData.getPaths().get(0).getTimeInMiliSeconds())
                                .build();
                        locationConnectionsDTOs.add(locationConnectionMapper.map(locationConnection));
                    }
                }
            }
        }

        final OptimizedStepsRequest request = OptimizedStepsRequest
                .builder()
                .depot(depot)
                .locationConnections(locationConnectionsDTOs)
                .limitImprovingHeuristicInSeconds(routeLocationsRequest.getLimitImprovingHeuristicInSeconds())
                .build();

        OptimizedStepsResponse optimizedSteps = routeStepsOptimizer.getOptimizedSteps(request);
        optimizedSteps.setDistanceInMeters(getRouteDistance(optimizedSteps, locationConnectionsDTOs));

        return optimizedSteps;
    }

    private int getRouteDistance(OptimizedStepsResponse optimizedSteps, Set<LocationConnectionDTO> locationConnectionsDTOs) {
        int totalDistance = 0;
        final List<LocationConnectionDTO> testList = new ArrayList<>();
        for(int i = 0; i < optimizedSteps.getOptimizedSteps().size() -1; i++){
            final LocationDTO startLocation = optimizedSteps.getOptimizedSteps().get(i).getLocation();
            final LocationDTO endLocation = optimizedSteps.getOptimizedSteps().get(i+1).getLocation();


            final LocationConnectionDTO locationConnection = locationConnectionsDTOs
                    .stream()
                    .filter(s->s.getStartLocation().equals(startLocation) && s.getEndLocation().equals(endLocation)).
                    findFirst()
                    .orElseThrow(NoSuchElementException::new);
            totalDistance = totalDistance + locationConnection.getDistanceInMeters();
            testList.add(locationConnection);
        }
        System.out.println(testList);
        return totalDistance;
    }

    private PathData getPathDataFromExternalAPI(LocationDTO startLocationRequest, LocationDTO endLocationRequest) {
        final String startPointCoordinates = "%s,%s".formatted(startLocationRequest.getLatitude(),
                startLocationRequest.getLongitude());
        final String endPointCoordinates = "%s,%s".formatted(endLocationRequest.getLatitude(),
                endLocationRequest.getLongitude());

        return webClient
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .queryParam("point", startPointCoordinates)
                                .queryParam("point", endPointCoordinates)
                                .queryParam("profile", "foot")
                                .build()
                )
                .retrieve()
                .bodyToMono(PathData.class)
                .block();
    }


}
