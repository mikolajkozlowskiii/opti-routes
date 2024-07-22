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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RouteResponseServiceImpl implements RouteResponseService {
    private final RouteService routeService;
    private final RouteMapper routeMapper;
    private final RouteStepService routeStepService;
    private final TagService tagService;
    private final UserService userService;
    private final UserRouteRatingService userRouteRatingService;
    private final UserSavedRoutesService userSavedRoutesService;
    private final RouteOptimizationService routeOptimizationService;

    private final WebClient webClient;

    public RouteResponseServiceImpl(RouteService routeService, RouteMapper routeMapper, RouteStepService routeStepService, TagService tagService, UserService userService, UserRouteRatingService userRouteRatingService, UserSavedRoutesService userSavedRoutesService, RouteOptimizationService routeOptimizationService,  @Qualifier("reverseGeocodingClient") WebClient webClient) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
        this.routeStepService = routeStepService;
        this.tagService = tagService;
        this.userService = userService;
        this.userRouteRatingService = userRouteRatingService;
        this.userSavedRoutesService = userSavedRoutesService;
        this.routeOptimizationService = routeOptimizationService;
        this.webClient = webClient;
    }

    @Override
    public RouteResponse createRouteWithOptimization(RouteRequest route, UserDetailsImpl userDetails) {
        if(route.isOptimized()){
            route = optimizeRoute(route);
        }
        return save(route, userDetails);
    }

    private RouteRequest optimizeRoute(RouteRequest route) {
        final Set<LocationDTO> locationDTOS = route.getLocationsSteps().stream().map(RouteStepDTO::getLocation).collect(Collectors.toSet());


        final Set<LocationDTO> locationsRequests = new HashSet<>();
        for(LocationDTO locationDTO : locationDTOS){
            if(StringUtils.isEmpty(locationDTO.getName())){
                locationsRequests.add(getLocationNameAndAddress(locationDTO));
            } else {
                locationsRequests.add(locationDTO);
            }
        }

        LocationDTO depot;
        if(StringUtils.isEmpty(route.getDepot().getName())){
            depot = getLocationNameAndAddress(route.getDepot());
        } else {
            depot = route.getDepot();
        }



            final OptimizedStepsResponse optimizedStepsResponse = routeOptimizationService.getOptimizedRoute(
                    RouteLocationsRequest
                            .builder()
                            .locations(locationsRequests)
                            .depot(depot)
                            .build());

            return RouteRequest
                    .builder()
                    .isOptimized(route.isOptimized())
                    .description(route.getDescription())
                    .locationsSteps(new HashSet<>(optimizedStepsResponse.getOptimizedSteps()))
                    .tags(route.getTags())
                    .isOptimized(route.isPublic())
                    .build();

    }

    private void checkStepsEnumeration(RouteRequest route) {
        if(!route.getLocationsSteps().contains(RouteStepDTO.builder().step(0).build())){
            route.getLocationsSteps().forEach(s->s.setStep(s.getStep()-1));
        }
    }

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

    private LocationDTO getLocationNameAndAddress(LocationDTO locationDTO) {
        final String point = "%s,%s".formatted(locationDTO.getLatitude(),
                locationDTO.getLongitude());


        // CHAGE BASE ADDRES FOR THIS AND FOR EHTH4ER
        final ReverseGeocodingResponse response = webClient
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .queryParam("point", point)
                                .queryParam("reverse", true)
                                .build()
                )
                .retrieve()
                .bodyToMono(ReverseGeocodingResponse.class)
                .block();

        final ReverseGeocodingLocation reverseGeocodingLocation = response
                .getHits()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Location name and address not found."));

        return LocationDTO
                .builder()
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .address(reverseGeocodingLocation.getAddress())
                .name(reverseGeocodingLocation.getName())
                .build();
    }
}
