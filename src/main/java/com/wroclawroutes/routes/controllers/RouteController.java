package com.wroclawroutes.routes.controllers;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.RouteRequest;
import com.wroclawroutes.routes.dto.RouteResponse;
import com.wroclawroutes.routes.dto.RouteStatsResponse;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.service.RouteResponseService;
import com.wroclawroutes.routes.service.RouteService;
import com.wroclawroutes.security.userdetails.CurrentUser;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/routes/")
@RequiredArgsConstructor
public class RouteController {
    private final RouteResponseService routeResponseService;
    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@RequestBody @Valid RouteRequest routeRequest,
                                                     @CurrentUser UserDetailsImpl currentUser){
        return ResponseEntity.ok(routeResponseService.save(routeRequest, currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(routeResponseService.findResponseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        routeResponseService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, "Route " + id + " has been deleted."));
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<Route> findFull(@PathVariable Long id){
        System.out.println("test");
        System.out.println("tests");
        return ResponseEntity.ok(routeService.findById(id));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<RouteStatsResponse> findByIdWithDetails(@PathVariable Long id){
        return ResponseEntity.ok(routeResponseService.findStatsResponseById(id));
    }

    @GetMapping("details")
    public ResponseEntity<List<RouteStatsResponse>> findAllWithDetails(){
        return ResponseEntity.ok(routeResponseService.findAllStatsResponse());
    }

    @GetMapping("details/{email}")
    public ResponseEntity<List<RouteStatsResponse>> findAllWithDetails(@PathVariable String email){
        return ResponseEntity.ok(routeResponseService.findAllStatsResponseByEmail(email));
    }

    @GetMapping("user-author/details")
    public ResponseEntity<List<RouteStatsResponse>> findAllWithDetailsCreatedByCurrentUser(@CurrentUser UserDetailsImpl currentUser){
        return ResponseEntity.ok(routeResponseService.findAllStatsResponseByAuthorCurrentUser(currentUser));
    }
}
