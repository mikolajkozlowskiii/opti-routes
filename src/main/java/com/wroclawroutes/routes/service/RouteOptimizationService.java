package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.dto.OptimizedStepsResponse;
import com.wroclawroutes.routes.dto.ProposedRouteReponse;
import com.wroclawroutes.routes.dto.RouteResponse;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.util.TSPInputDataR;

import java.util.Set;

public interface RouteOptimizationService {
    OptimizedStepsResponse getOptimizedRoute(Set<Location> locations, Location depot);
    OptimizedStepsResponse getOptimizedRoute(Set<Long> locationsId, Long depotId);
}
