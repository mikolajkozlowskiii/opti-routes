package com.wroclawroutes.routes.controllers;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.LocationLikeRequest;
import com.wroclawroutes.routes.dto.RatingRequest;
import com.wroclawroutes.routes.service.UserLocationLikedService;
import com.wroclawroutes.routes.service.UserRouteRatingService;
import com.wroclawroutes.routes.service.UserSavedRoutesService;
import com.wroclawroutes.security.userdetails.CurrentUser;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/locations/")
@RequiredArgsConstructor
public class LocationController {
    private final UserLocationLikedService userLocationLikedService;
    @PutMapping("/{locationId}/likes")
    public ResponseEntity<ApiResponse> rateRoute(@PathVariable Long locationId,
                                                 @CurrentUser UserDetailsImpl currentUser,
                                                 @RequestBody @Valid LocationLikeRequest likeRequest){
        return ResponseEntity.ok(userLocationLikedService.save(locationId, currentUser, likeRequest));
    }
}
