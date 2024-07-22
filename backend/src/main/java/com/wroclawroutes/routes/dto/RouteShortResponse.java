package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RouteShortResponse {
    @JsonProperty("distance_in_meters")
    private Integer distanceInMeters;
    @JsonProperty("time_in_mili_seconds")
    private Integer timeInMiliSeconds;
    private List<RouteStepDTO> steps;
}
