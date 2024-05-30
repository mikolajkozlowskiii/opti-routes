package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.RatingRequest;
import com.wroclawroutes.routes.dto.RouteRatingsResponse;
import com.wroclawroutes.routes.dto.UserRouteRatingResponse;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.routes.repository.UserRouteRatingRepository;
import com.wroclawroutes.routes.service.RouteService;
import com.wroclawroutes.routes.service.UserRouteRatingService;
import com.wroclawroutes.routes.service.mapper.UserActivityMapper;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.entities.ERole;
import com.wroclawroutes.users.entities.Role;
import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.users.services.RoleService;
import com.wroclawroutes.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserRouteRatingServiceImpl implements UserRouteRatingService {
    private final UserRouteRatingRepository userRouteRatingRepository;
    private final UserService userService;
    private final RouteService routeService;
    private final UserActivityMapper userActivityMapper;
    private final RoleService roleService;
    @Override
    public List<UserRouteRating> findAllByUserOrderByCreatedAtDesc(User user) {
        return userRouteRatingRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<UserRouteRating> findAllByRoute(Route route) {
        return userRouteRatingRepository.findAllByRoute(route);
    }

    @Override
    public List<UserRouteRatingResponse> findUserRouteRatingResponsesByUser(UserDetailsImpl currentUser) {
        final User user = userService.getCurrentUser(currentUser);
        final List<UserRouteRating> userRouteRatings = userRouteRatingRepository.findAllByUserOrderByCreatedAtDesc(user);
        return userRouteRatings.stream().map(s-> userActivityMapper.mapToUserRouteRatingResponse(s, getAllUsersRouteRatingResponseByRoute(s.getRoute()), getGuideUsersRouteRatingResponseByRoute(s.getRoute()))).toList();
    }

    @Override
    public RouteRatingsResponse getGuideUsersRouteRatingResponseByRoute(Route route) {
        final Role roleGuide = roleService.getRole(ERole.ROLE_GUIDE);
        final List<UserRouteRating> ratings = findAllByRoute(route)
                .stream()
                .filter(s->s.getUser().getRoles().contains(roleGuide))
                .toList();
        return getRouteRatingsResponse(ratings);
    }

    @Override
    public RouteRatingsResponse getAllUsersRouteRatingResponseByRoute(Route route) {
        final Role roleUser = roleService.getRole(ERole.ROLE_USER);
        final List<UserRouteRating> ratings = new ArrayList<>(findAllByRoute(route)
                .stream()
                .filter(s -> s.getUser().getRoles().contains(roleUser) && s.getUser().getRoles().size() == 1)
                .toList());
        Collections.sort(ratings);
        return getRouteRatingsResponse(ratings);
    }

    private RouteRatingsResponse getRouteRatingsResponse(List<UserRouteRating> ratings) {
        final double average = ratings.stream().mapToDouble(s->s.getRating()).average().orElse(0);
        return RouteRatingsResponse
                .builder()
                .averageRating(average)
                .userRatingResponse(ratings.stream().map(userActivityMapper::mapToUserRatingResponse).toList())
                .build();
    }

    @Override
    public ApiResponse saveRate(UserDetailsImpl currentUser, Long routeId, RatingRequest ratingRequest) {
        // if route hase rate it should be replaced, so findbyUserAndRoute and then set this rate and save.... a moze bez tego zadziala? bo jest kluz unikalnosci zobaczymy
        final User user = userService.getCurrentUser(currentUser);
        final Route route = routeService.findById(routeId);
        UserRouteRating userRouteRating = userRouteRatingRepository
                .findByRouteAndUser(route, user)
                .orElse(UserRouteRating
                        .builder()
                        .user(user)
                        .rating(ratingRequest.getRating())
                        .route(route)
                        .build());

        userRouteRating.setRating(ratingRequest.getRating());
        userRouteRating.setCreatedAt(Instant.now());
        userRouteRatingRepository.save(userRouteRating);

        return new ApiResponse(Boolean.TRUE, "Route id: " + routeId + ", rate: " + userRouteRating.getRating() );
    }

    @Override
    public Integer isRated(UserDetailsImpl currentUser, Long routeId) {
        final User user = userService.getCurrentUser(currentUser);
        final Route route = routeService.findById(routeId);
        UserRouteRating userRouteRating = userRouteRatingRepository.findByRouteAndUser(route, user).orElse(null);
        if(userRouteRating == null){
            return null;
        }
        return userRouteRating.getRating();
    }

    @Override
    public ApiResponse deleteRate(UserDetailsImpl currentUser, Long routeId, RatingRequest ratingRequest) {
        return null;
    }

    @Override
    public void unSaveRoute(UserDetailsImpl currentUser, Long routeId) {
        final User user = userService.getCurrentUser(currentUser);
        final Route route = routeService.findById(routeId);
        UserRouteRating userRouteRating = userRouteRatingRepository.findByRouteAndUser(route, user).orElse(null);
        if(userRouteRating == null){
            throw new NoSuchElementException("error!!!!");
        }
        userRouteRatingRepository.delete(userRouteRating);
    }
}
