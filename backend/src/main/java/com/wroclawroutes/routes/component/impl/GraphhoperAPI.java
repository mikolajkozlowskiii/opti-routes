package com.wroclawroutes.routes.component.impl;

import com.wroclawroutes.routes.component.LocationConnectionAPI;
import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.entity.RouteStep;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Component
public class GraphhoperAPI implements LocationConnectionAPI {
    private WebClient webClient;
    public GraphhoperAPI(@Qualifier("routeClient") WebClient webClient) {
        this.webClient = webClient;
    }
    @Override
    public LocationConnection getLocationConnection(Location startPoint, Location endPoint) {
        final PathData pathData = getPathDataFromExternalAPI(startPoint, endPoint);
        return LocationConnection
                .builder()
                .startLocation(startPoint)
                .endLocation(endPoint)
                .distanceInMeters(pathData.getPaths().get(0).getDistanceInMeters())
                .timeOnFootInSec(pathData.getPaths().get(0).getTimeInMiliSeconds())
                .build();
    }

    @Override
    public RouteParameters getRouteParameters(List<RouteStep> steps) {
        int totalDistance = 0;
        int totalTime = 0;
        List<PathData> testList = new ArrayList<>();
        for(int i = 0; i < steps.size() -1; i++){
            final int indexStepOfStart = i;
            final RouteStep startStep = steps
                    .stream()
                    .filter(s->s.getStep() == indexStepOfStart)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("There is no step: " + indexStepOfStart));

            final int indexStepOfEnd = i+1;

            final RouteStep endStep = steps
                    .stream()
                    .filter(s->s.getStep()==indexStepOfEnd)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("There is no step: " + indexStepOfEnd));


            final Location startLocation = startStep.getLocation();
            final Location endLocation = endStep.getLocation();
            PathData pathData = getPathDataFromExternalAPI(startLocation, endLocation);


            testList.add(pathData);
            totalDistance = totalDistance + pathData.getPaths().get(0).getDistanceInMeters();
            totalTime = totalTime + pathData.getPaths().get(0).getTimeInMiliSeconds();
        }
        System.out.println(testList);
        return RouteParameters
                .builder()
                .distanceInMeters(totalDistance)
                .timeInMiliSeconds(totalTime)
                .build();
    }

    private PathData getPathDataFromExternalAPI(LocationDTO startLocationRequest, LocationDTO endLocationRequest) {
        System.out.println("tests");
        System.out.println(startLocationRequest.getLatitude());
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

    private PathData getPathDataFromExternalAPI(Location startLocation, Location endLocation) {
        final String startPointCoordinates = "%s,%s".formatted(startLocation.getLatitude(),
                startLocation.getLongitude());
        final String endPointCoordinates = "%s,%s".formatted(endLocation.getLatitude(),
                endLocation.getLongitude());

        System.out.println(startPointCoordinates);
        System.out.println(endPointCoordinates);
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
