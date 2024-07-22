package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.dto.RouteStatsResponse;
import com.wroclawroutes.routes.dto.RouteStepDTO;
import com.wroclawroutes.routes.dto.RouteStepStatsResponse;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;

import java.util.List;
import java.util.Set;

public interface RouteStepService {
    RouteStep save(RouteStep step);
    List<RouteStep> save(List<RouteStep> step);
    Set<RouteStep> createStepsEntity(Set<RouteStepDTO> step);
    RouteStep findById(Long id);
    List<RouteStepStatsResponse> findAllResponsesByRoute(Route route);
    List<RouteStep> findAllByRoute(Route route);
}
