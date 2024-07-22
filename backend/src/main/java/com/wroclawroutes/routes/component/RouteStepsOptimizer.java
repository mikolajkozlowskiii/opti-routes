package com.wroclawroutes.routes.component;

import com.wroclawroutes.routes.dto.OptimizedStepsRequest;
import com.wroclawroutes.routes.dto.OptimizedStepsResponse;

public interface RouteStepsOptimizer {
    OptimizedStepsResponse getOptimizedSteps(OptimizedStepsRequest request);
}
