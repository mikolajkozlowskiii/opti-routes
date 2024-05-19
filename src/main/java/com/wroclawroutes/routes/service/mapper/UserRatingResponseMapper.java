package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.UserRatingResponse;
import com.wroclawroutes.routes.dto.UserRouteRatingResponse;
import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.users.services.components.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRatingResponseMapper {
    private final RouteMapper routeMapper;
    private final UserMapper userMapper;
    public UserRatingResponse mapToUserRatingResponse(UserRouteRating userRouteRating){
        return UserRatingResponse
                .builder()
                .rate(userRouteRating.getRating())
                .userEmail(userRouteRating.getUser().getEmail())
                .ratedAt(userRouteRating.getCreatedAt())
                .build();
    }

    public UserRouteRatingResponse map(UserRouteRating userRouteRating){
        return UserRouteRatingResponse
                .builder()
                .rating(userRouteRating.getRating())
                .user(userMapper.map(userRouteRating.getUser()))
                .route(routeMapper.map(userRouteRating.getRoute()))
                .ratedAt(userRouteRating.getCreatedAt())
                .build();
    }
}
