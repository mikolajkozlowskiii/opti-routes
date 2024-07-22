package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.util.TSPInputDataR;

import java.util.Set;

public interface RouteOptimizationService {
    OptimizedStepsResponse getOptimizedRoute(RouteLocationsRequest routeLocationsRequest);
}
