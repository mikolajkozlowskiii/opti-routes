package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.RouteRatingsResponse;
import com.wroclawroutes.routes.dto.UserRatingResponse;
import com.wroclawroutes.routes.dto.UserRouteRatingResponse;
import com.wroclawroutes.routes.dto.UserRouteSavedResponse;
import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.routes.entity.UserRouteSaved;
import com.wroclawroutes.users.services.components.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class UserActivityMapper {
   // private final RouteMapper routeMapper;
    private final UserMapper userMapper;
    // private final UserResponseService userResponseService;
    private final RouteStatsResponseMapper routeStatsResponseMapper;

    public UserRatingResponse mapToUserRatingResponse(UserRouteRating userRouteRating){
        return UserRatingResponse
                .builder()
                .rate(userRouteRating.getRating())
                .userEmail(userRouteRating.getUser().getEmail())
                .ratedAt(userRouteRating.getCreatedAt())
                .build();
    }

    public UserRouteRatingResponse mapToUserRouteRatingResponse(UserRouteRating userRouteRating, RouteRatingsResponse allUsersRouteRatingResponseByRoute, RouteRatingsResponse onlyGuidesRouteRatingResponseByRoute){
        return UserRouteRatingResponse
                .builder()
                .rating(userRouteRating.getRating())
                .user(userMapper.map(userRouteRating.getUser()))
                .route(routeStatsResponseMapper.map(userRouteRating.getRoute(), allUsersRouteRatingResponseByRoute, onlyGuidesRouteRatingResponseByRoute)) // TO NEI DZIALA, TRZBA WZIAC IMPLEMENTACJE METODY MAP Z SERIWUS RouteResponseServiceImpl!!!
                .ratedAt(LocalDateTime.ofInstant(userRouteRating.getCreatedAt(), ZoneId.of("CET")))
                .build();
    }

    public UserRouteSavedResponse mapUserRouteSavedResponse(UserRouteSaved userRouteSaved, RouteRatingsResponse allUsersRouteRatingResponseByRoute, RouteRatingsResponse onlyGuidesRouteRatingResponseByRoute){
        return UserRouteSavedResponse
                .builder()
                .user(userMapper.map(userRouteSaved.getUser()))
                .savedAt(userRouteSaved.getCreatedAt())
                .route(routeStatsResponseMapper.map(userRouteSaved.getRoute(), allUsersRouteRatingResponseByRoute, onlyGuidesRouteRatingResponseByRoute))
                .build();
    }
}
