package com.wroclawroutes.routes.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ProposedRouteReponse {
    private Long distanceInMeters;
    private Long timeInSeconds;
    private Set<OptimizedStepsResponse> steps;
}
