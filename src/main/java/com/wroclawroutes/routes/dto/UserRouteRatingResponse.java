package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wroclawroutes.users.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class UserRouteRatingResponse {
    @JsonProperty("userWhoRated")
    private UserResponse user;
    @JsonProperty("routeDetails")
    private RouteStatsResponse route;
    @JsonProperty("rated_at")
    private LocalDateTime ratedAt;
    @JsonProperty("rate")
    private Integer rating;
}
