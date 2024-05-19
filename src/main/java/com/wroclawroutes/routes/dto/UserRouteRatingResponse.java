package com.wroclawroutes.routes.dto;

import com.wroclawroutes.users.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
@Data
@Builder
public class UserRouteRatingResponse {
    private UserResponse user;
    private RouteStatsResponse route;
    private Instant ratedAt;
    private Integer rating;
}
