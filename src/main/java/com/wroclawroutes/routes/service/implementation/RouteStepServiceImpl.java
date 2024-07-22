package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.dto.LocationDTO;
import com.wroclawroutes.routes.dto.LocationStatResponse;
import com.wroclawroutes.routes.dto.RouteStepDTO;
import com.wroclawroutes.routes.dto.RouteStepStatsResponse;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import com.wroclawroutes.routes.repository.RouteStepRepository;
import com.wroclawroutes.routes.service.LocationService;
import com.wroclawroutes.routes.service.RouteStepService;
import com.wroclawroutes.routes.service.mapper.LocationMapper;
import com.wroclawroutes.routes.service.mapper.RouteStepMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteStepServiceImpl implements RouteStepService {
    private final RouteStepRepository routeStepRepository;
    private final LocationService locationService;
    private final RouteStepMapper routeStepMapper;
    @Override
    public RouteStep save(RouteStep step) {
        return routeStepRepository.save(step);
    }

    @Override
    public List<RouteStep> save(List<RouteStep> steps) {
        for(RouteStep routeStep : steps){
            Location currentLocation = routeStep.getLocation();
            if(!locationService.existsByLatitudeAndLongitude(currentLocation.getLatitude(), currentLocation.getLongitude())){
                locationService.save(routeStep.getLocation());
            }else{
                currentLocation = locationService.findByLatitudeAndLongitude(currentLocation.getLatitude(), currentLocation.getLongitude());
            }
            routeStep.setLocation(currentLocation);
            routeStep.setRoute(routeStep.getRoute());
          //  routeStepRepository.save(routeStep);
        }
        return routeStepRepository.saveAll(steps);
    }

    @Override
    public Set<RouteStep> createStepsEntity(Set<RouteStepDTO> steps) {
        final Set<RouteStep> entities = new HashSet<>();
        // TODO validation, check if step cant be out of the <0, size-1>, and locations cant be the same if depot
        for(RouteStepDTO routeStepDTO : steps){
            entities.add(routeStepMapper.map(routeStepDTO));
        }
        return entities;
    }

    @Override
    public RouteStep findById(Long id) {
        return routeStepRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("RouteStep with id: %d not found.".formatted(id)));
    }

    @Override
    public List<RouteStepStatsResponse> findAllResponsesByRoute(Route route) {
        final List<RouteStep> routeSteps = findAllByRoute(route);
        final List<RouteStepStatsResponse> routeStepStatsResponses = new ArrayList<>();
        for(RouteStep routeStep : routeSteps){
            int numOfLikes = routeStep.getLocation().getLikedByUsers()
                    .stream()
                    .mapToInt(s->s.getIsLiked()?1:-1)
                    .sum();
            final LocationStatResponse locationStatResponse = routeStepMapper.mapToLocationStatResponse(routeStep, numOfLikes);
            routeStepStatsResponses.add(
                    RouteStepStatsResponse
                            .builder()
                            .step(routeStep.getStep())
                            .location(locationStatResponse)
                            .build()
            );
        }
        Collections.sort(routeStepStatsResponses);
        return routeStepStatsResponses;
    }

    @Override
    public List<RouteStep> findAllByRoute(Route route) {
        return routeStepRepository.findAllByRoute(route);
    }
}/*
public class LocationStatResponse {
    private LocationDTO locationInfo;
    private Integer numOfLikes;
}

@Builder
public class RouteStepStatsResponse {
    private LocationStatResponse location;
    private Integer step;
}*/