package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class OptimizedStepsResponse {
    @JsonProperty("total_time_in_milliseconds")
    private long totalTimeInSeconds;
    @JsonProperty("distance_in_meters")
    private long distanceInMeters;
    @JsonProperty("optimized_steps")
    private List<RouteStepDTO> optimizedSteps;
}
