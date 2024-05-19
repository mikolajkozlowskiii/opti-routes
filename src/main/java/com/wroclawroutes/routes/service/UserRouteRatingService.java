package com.wroclawroutes.routes.service;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.RatingRequest;
import com.wroclawroutes.routes.dto.RouteRatingsResponse;
import com.wroclawroutes.routes.dto.RouteStepDTO;
import com.wroclawroutes.routes.dto.UserRouteRatingResponse;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.routes.entity.UserRouteSaved;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.entities.User;

import java.util.List;

public interface UserRouteRatingService {
    List<UserRouteRating> findAllByUserOrderByCreatedAtDesc(User user);
    List<UserRouteRating> findAllByRoute(Route route);
    List<UserRouteRatingResponse> findAllStatsResponseByAuthorCurrentUser(UserDetailsImpl currentUser);
    RouteRatingsResponse getGuideUsersRouteRatingResponseByRoute(Route route);
    RouteRatingsResponse getAllUsersRouteRatingResponseByRoute(Route route);
    ApiResponse saveRate(UserDetailsImpl currentUser, Long routeId, RatingRequest ratingRequest);
    Integer isRated(UserDetailsImpl currentUser, Long routeId);
    ApiResponse deleteRate(UserDetailsImpl currentUser, Long routeId, RatingRequest ratingRequest);
    void unSaveRoute(UserDetailsImpl currentUser, Long routeId);
}
