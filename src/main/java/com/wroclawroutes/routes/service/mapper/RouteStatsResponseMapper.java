package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.service.RouteStepService;
import com.wroclawroutes.routes.service.UserRouteRatingService;
import com.wroclawroutes.routes.service.UserSavedRoutesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteStatsResponseMapper {
    private final RouteMapper routeMapper;
    private final RouteStepService routeStepService;
    
    public RouteStatsResponse map(Route route, RouteRatingsResponse allUsersRouteRatingResponseByRoute, RouteRatingsResponse onlyGuidesRouteRatingResponseByRoute){
        final RouteResponse routeResponse = routeMapper.mapToRouteResponse(route);
        final long numOfRouteSavedTimes = route.getUsersSavedRoutes().size();
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
}
