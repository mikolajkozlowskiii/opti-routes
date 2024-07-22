package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.component.LocationConnectionAPI;
import com.wroclawroutes.routes.dto.RouteParameters;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import com.wroclawroutes.routes.entity.Tag;
import com.wroclawroutes.routes.exceptions.RouteNotFoundException;
import com.wroclawroutes.routes.repository.RouteRepository;
import com.wroclawroutes.routes.service.RouteService;
import com.wroclawroutes.routes.service.RouteStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final RouteStepService routeStepService;
    private final LocationConnectionAPI locationConnectionAPI;

    @Override
    public Route findById(Long id) {
        return routeRepository
                .findByIdFetchAllRelationships(id)
                .orElseThrow(() -> new RouteNotFoundException(id));
    }

    @Override
    public Route findByIdFetchAllRelationships(Long id) {
        return routeRepository
                .findByIdFetchAllRelationships(id)
                .orElseThrow(() -> new RouteNotFoundException(id));
    }

    @Override
    public void delete(Route route) {
        routeRepository.delete(route);
    }

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public List<Route> findAllFetchAllRelationships() {
        return routeRepository.findAllFetchStepsWithLocationsEagerly();
    }

    @Override
    public List<Route> findAllFetchAllRelationshipsByEmail(String email) {
        return routeRepository.findAllFetchStepsWithLocationsEagerlyByEmail(email);
    }

    @Override
    public List<Route> findAllByTagsContains(Tag tag) {
        return routeRepository.findAllByTagsContains(tag);
    }

    @Override
    public List<Route> findAllByTagContainsFetchRatingsAndTagsEagerly(Tag tag) {
        return routeRepository.findAllByTagContainsFetchRatingsAndTagsEagerly(tag);
    }

    @Override
    public List<Route> findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set<Tag> tags) {
        return routeRepository.findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(tags);
    }

    @Override
    public List<Route> findAllByOnlyTags(Set<Tag> tags) {
        final int tagCount = tags.size();
        return routeRepository.findAllByOnlyTags(tags, tagCount);
    }

    @Override
    public List<Route> findAllFetchTagsAndRatingsEagerly() {
        return routeRepository.findAllFetchTagsAndRatingsEagerly();
    }

    @Override
    public Route save(Route route) {
        final RouteParameters routeParameters = locationConnectionAPI.getRouteParameters(route.getSteps().stream().toList());
        route.setTimeInMilliseconds(routeParameters.getTimeInMiliSeconds());
        route.setDistanceInMeters(routeParameters.getDistanceInMeters());
        List<RouteStep> steps2 = routeStepService.save(route.getSteps().stream().toList());

        System.out.println("test size "+route.getSteps().size());
        for (RouteStep routeStep : steps2) {
            route.addRouteStep(routeStep);
        }
    //    route.getSteps().forEach(route::addRouteStep);
        System.out.println(route.getSteps().size());


        Route route1Saved =  routeRepository.save(route);


        Set<RouteStep> steps = route1Saved.getSteps().stream().collect(Collectors.toSet());
        route1Saved.setSteps(steps);
        return route1Saved;
    }



    @Override
    public Route findByIdFetchStepsEagerly(Long id) {
        return routeRepository
                .findByIdFetchStepsWithLocationsEagerly(id)
                .orElseThrow(() -> new RouteNotFoundException(id));
    }


}
