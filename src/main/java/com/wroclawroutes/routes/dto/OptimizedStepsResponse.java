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
    @JsonProperty("total_time_in_seconds")
    private long totalTimeInSeconds;
    @JsonProperty("optimized_steps")
    private List<RouteStepResponse> optimizedSteps;
}
