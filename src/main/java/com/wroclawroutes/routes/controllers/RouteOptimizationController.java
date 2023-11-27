package com.wroclawroutes.routes.controllers;

import com.wroclawroutes.routes.dto.LocationRequest;
import com.wroclawroutes.routes.dto.PathData;
import com.wroclawroutes.routes.dto.OptimizedStepsResponse;
import com.wroclawroutes.routes.dto.RouteLocationsRequest;
import com.wroclawroutes.routes.service.RouteOptimizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/route/optimization/")
@RequiredArgsConstructor
public class RouteOptimizationController {
    private final RouteOptimizationService routeOptimizationService;
    @PostMapping
    public ResponseEntity<OptimizedStepsResponse> findAllUsers(@Valid @RequestBody RouteLocationsRequest routeLocationsRequest) {
        return ResponseEntity.ok(routeOptimizationService.getOptimizedRoute(routeLocationsRequest));
    }
}
