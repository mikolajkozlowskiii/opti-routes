package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;

import java.util.List;

public interface RouteStepService {
    RouteStep save(RouteStep step);
    RouteStep findById(Long id);
    List<RouteStep> findAllByRoute(Route route);
}
