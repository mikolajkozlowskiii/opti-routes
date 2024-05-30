package com.wroclawroutes.routes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteOptimizationResponse {
    private RouteResponse response;
    private OptimizationGain optimizationGain;
    private RouteShortResponse nonOptimizedRoute;
}
