package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;
import com.wroclawroutes.routes.service.*;
import com.wroclawroutes.routes.service.mapper.RouteMapper;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class RouteResponseServiceImpl implements RouteResponseService {
    private final RouteService routeService;
    private final RouteMapper routeMapper;
    private final RouteStepService routeStepService;
    private final TagService tagService;
    private final UserService userService;
    private final UserRouteRatingService userRouteRatingService;
    private final UserSavedRoutesService userSavedRoutesService;


    @Override
    public RouteResponse save(RouteRequest routeRequest, UserDetailsImpl userDetails) {
        // TODO IF TAG IS IN DB, IF NOT THROW EXCEPTION, LOCATION DONT HAVE TO BD IN DB IT SHOULD AUTOMATICALLY SAVE IT ;)
        Route route = routeMapper.mapToRouteResponse(routeRequest);
        route.setUser(userService.getCurrentUser(userDetails));

        route.setSteps(routeStepService.createStepsEntity(routeRequest.getLocationsSteps()));
        route.setTags(tagService.getEntityTags(routeRequest.getTags()));
        return routeMapper.mapToRouteResponse(routeService.save(route));
    }

    @Override
    public void deleteById(Long id) {
        final Route route = routeService.findById(id);
        routeService.delete(route);
    }

    @Override
    public void delete(Route route, UserDetailsImpl userDetails) {

    }

    @Override
    public RouteResponse findById(Long id) {
        return null;
    }

    @Override
    public RouteStatsResponse findRouteStatsById(Long id) {
        Route route = routeService.findByIdFetchAllRelationships(id);


        RouteResponse routeResponse = routeMapper.mapToRouteResponse(route);
        return RouteStatsResponse
                .builder()
                .author(routeResponse.getUser())
                .description(routeResponse.getDescription())
                .createdAt(routeResponse.getCreatedAt())
                .isPublic(routeResponse.getIsPublic())
                .timeInMiliSeconds(routeResponse.getTimeInMiliSeconds())
                .distanceInMeters(routeResponse.getDistanceInMeters())
                .tags(routeResponse.getTags())
                .usersRatings(null)
                .guideUsersRatings(null)
                .savedTimes(null)
                .steps(null)
                .build();
    }

    @Override
    public Route findByIdFetchStepsEagerly(Long id) {
        return null;
    }

//    @Override
//    public Route findByIdFetchStepsEagerly(Long id) {
//        Route route = routeService.findAllF(id);
//
//    }

    @Override
    public RouteResponse findResponseById(Long id) {
        System.out.println("WYSLANE ZAPYTANIE");
        return routeMapper.mapToRouteResponse(routeService.findByIdFetchAllRelationships(id));
    }

    @Override
    public RouteStatsResponse findStatsResponseById(Long id) {
        final Route route = routeService.findById(id);
        final RouteResponse routeResponse = routeMapper.mapToRouteResponse(route);
        final RouteRatingsResponse allUsersRouteRatingResponseByRoute = userRouteRatingService.getAllUsersRouteRatingResponseByRoute(route);
        final RouteRatingsResponse onlyGuidesRouteRatingResponseByRoute = userRouteRatingService.getGuideUsersRouteRatingResponseByRoute(route);
        final long numOfRouteSavedTimes = userSavedRoutesService.numberOfRouteSavedTimes(route);
        final List<RouteStepStatsResponse> routeStepStatsResponses = routeStepService.findAllResponsesByRoute(route);
        return RouteStatsResponse
                .builder()
                .id(routeResponse.getId())
                .author(routeResponse.getUser())
                .tags(routeResponse.getTags())
                .isPublic(routeResponse.getIsPublic())
                .createdAt(routeResponse.getCreatedAt())
                .distanceInMeters(routeResponse.getDistanceInMeters())
                .timeInMiliSeconds(routeResponse.getTimeInMiliSeconds())
                .description(routeResponse.getDescription())
                .steps(routeStepStatsResponses)
                .savedTimes(numOfRouteSavedTimes)
                .usersRatings(allUsersRouteRatingResponseByRoute)
                .guideUsersRatings(onlyGuidesRouteRatingResponseByRoute)
                .build();
    }

    @Override
    public List<RouteStatsResponse> findAllStatsResponse() {
        List<Route> routes = routeService.findAllFetchAllRelationships();

        return routes.stream().map(s->map(s)).toList();
    }

    @Override
    public List<RouteStatsResponse> findAllStatsResponseByEmail(String email) {
        List<Route> routes = routeService.findAllFetchAllRelationshipsByEmail(email);

        return routes.stream().map(s->map(s)).toList();
    }

    @Override
    public List<RouteStatsResponse> findAllStatsResponseByAuthorCurrentUser(UserDetailsImpl currentUser) {
        final User user = userService.getCurrentUser(currentUser);
        return findAllStatsResponseByEmail(user.getEmail());
    }

    private RouteStatsResponse map(Route route){
        final RouteResponse routeResponse = routeMapper.mapToRouteResponse(route);
        final RouteRatingsResponse allUsersRouteRatingResponseByRoute = userRouteRatingService.getAllUsersRouteRatingResponseByRoute(route);
        final RouteRatingsResponse onlyGuidesRouteRatingResponseByRoute = userRouteRatingService.getGuideUsersRouteRatingResponseByRoute(route);
        final long numOfRouteSavedTimes = userSavedRoutesService.numberOfRouteSavedTimes(route);
        final List<RouteStepStatsResponse> routeStepStatsResponses = routeStepService.findAllResponsesByRoute(route);
        return RouteStatsResponse
                .builder()
                .id(routeResponse.getId())
                .author(routeResponse.getUser())
                .tags(routeResponse.getTags())
                .isPublic(routeResponse.getIsPublic())
                .createdAt(routeResponse.getCreatedAt())
                .distanceInMeters(routeResponse.getDistanceInMeters())
                .timeInMiliSeconds(routeResponse.getTimeInMiliSeconds())
                .description(routeResponse.getDescription())
                .steps(routeStepStatsResponses)
                .savedTimes(numOfRouteSavedTimes)
                .usersRatings(allUsersRouteRatingResponseByRoute)
                .guideUsersRatings(onlyGuidesRouteRatingResponseByRoute)
                .build();
    }

    @Override
    public List<Route> findAll() {
        return null;
    }

    @Override
    public List<Route> findAllFetchTagsAndRatingsEagerly() {
        return null;
    }

    @Override
    public List<Route> findAllByTagsContains(Tag tag) {
        return null;
    }

    @Override
    public List<Route> findAllByTagContainsFetchRatingsAndTagsEagerly(Tag tag) {
        return null;
    }

    @Override
    public List<Route> findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set<Tag> tag) {
        return null;
    }

    @Override
    public List<Route> findAllByOnlyTags(Set<Tag> tags) {
        return null;
    }
}
