package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wroclawroutes.users.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@Builder
public class RouteResponse {
    private Long id;
    private UserResponse user;
    private String description;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("distance_in_meters")
    private Integer distanceInMeters;
    @JsonProperty("time_in_mili_seconds")
    private Integer timeInMiliSeconds;
    @JsonProperty("is_public")
    private Boolean isPublic;
    private List<TagDTO> tags;
    private List<RouteStepDTO> steps;
}
