package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wroclawroutes.users.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RouteStatsResponse {
    private Long id;
    private String description;
    private UserResponse author;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("distance_in_meters")
    private Integer distanceInMeters;
    @JsonProperty("time_in_mili_seconds")
    private Integer timeInMiliSeconds;
    @JsonProperty("saved_times")
    private Long savedTimes;
    private RouteRatingsResponse usersRatings;
    private RouteRatingsResponse guideUsersRatings;
    private List<RouteStepStatsResponse> steps;
    private List<TagDTO> tags;
    @JsonProperty("is_public")
    private Boolean isPublic;
}
