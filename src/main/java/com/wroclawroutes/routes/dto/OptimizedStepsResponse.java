package com.wroclawroutes.routes.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OptimizedStepsResponse {
    private long value;
    private List<RouteStepResponse> optimizedSteps;
}
