package com.wroclawroutes.routes.controllers;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.RatingRequest;
import com.wroclawroutes.routes.dto.UserRouteRatingResponse;
import com.wroclawroutes.routes.dto.UserRouteSavedResponse;
import com.wroclawroutes.routes.service.UserRouteRatingService;
import com.wroclawroutes.routes.service.UserSavedRoutesService;
import com.wroclawroutes.security.userdetails.CurrentUser;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/routes/")
@RequiredArgsConstructor
public class UserActivityController {
    private final UserRouteRatingService userRouteRatingService;
    private final UserSavedRoutesService userSavedRoutesService;
    @GetMapping("/ratings")
    public ResponseEntity<ApiResponse> allRatings(@PathVariable Long routeId,
                                                 @CurrentUser UserDetailsImpl currentUser){
        return null;
    }
    @GetMapping("/{routeId}/ratings")
    public ResponseEntity<Integer> isRouteRated(@PathVariable Long routeId,
                                                 @CurrentUser UserDetailsImpl currentUser){
        return ResponseEntity.ok(userRouteRatingService.isRated(currentUser, routeId));
    }
    @PutMapping("/{routeId}/ratings")
    public ResponseEntity<ApiResponse> rateRoute(@PathVariable Long routeId,
                                                 @CurrentUser UserDetailsImpl currentUser,
                                                 @RequestBody @Valid RatingRequest rating){
        System.out.println("hambur");
        final ApiResponse apiResponse = userRouteRatingService.saveRate(currentUser, routeId, rating);

        System.out.println(apiResponse);
        return ResponseEntity.ok(apiResponse);
    }
    // TODO change it for put to like and dislike and based on that make like or unlike, bacause deletampping should be for deleting like or unlike
    @DeleteMapping("/{routeId}/ratings")
    public ResponseEntity<ApiResponse> unRateRoute(@PathVariable Long routeId,
                                                 @CurrentUser UserDetailsImpl currentUser){
        userRouteRatingService.unSaveRoute(currentUser, routeId);
        return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, "Rate of route " + routeId + " has been deleted."));
    }

    @GetMapping("/{routeId}/saves")
    public ResponseEntity<Boolean> isRouteSaved(@PathVariable Long routeId,
                                                 @CurrentUser UserDetailsImpl currentUser){
        return ResponseEntity.ok(userSavedRoutesService.isRouteSaved(currentUser, routeId));
    }

    @PutMapping("/{routeId}/saves")
    public ResponseEntity<ApiResponse> saveRoute(@PathVariable Long routeId,
                                                 @CurrentUser UserDetailsImpl currentUser){
        final ApiResponse apiResponse = userSavedRoutesService.save(currentUser, routeId);
        if(!apiResponse.getSuccess()){
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(apiResponse);
    }
    // TODO change it for put to like and dislike and based on that make like or unlike, bacause deletampping should be for deleting like or unlike
    @DeleteMapping("/{routeId}/saves")
    public ResponseEntity<ApiResponse> unSaveRoute(@PathVariable Long routeId,
                                                   @CurrentUser UserDetailsImpl currentUser){
        final ApiResponse apiResponse = userSavedRoutesService.unSave(currentUser, routeId);
        if(!apiResponse.getSuccess()){
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("user-author/details/rating")
    public ResponseEntity<List<UserRouteRatingResponse>> findAllSavedByCurrentUserWithDetails(@CurrentUser UserDetailsImpl currentUser){
        return ResponseEntity.ok(userRouteRatingService.findUserRouteRatingResponsesByUser(currentUser));
    }

    @GetMapping("user-author/details/saves")
    public ResponseEntity<List<UserRouteSavedResponse>> findAllRatedByCurrentUserWithDetails(@CurrentUser UserDetailsImpl currentUser){
        return ResponseEntity.ok(userSavedRoutesService.findUserRouteSavedResponseByUser(currentUser));
    }
}
