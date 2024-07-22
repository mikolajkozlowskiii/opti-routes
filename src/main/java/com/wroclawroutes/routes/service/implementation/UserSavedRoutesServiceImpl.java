package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.UserRouteSavedResponse;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.UserRouteSaved;
import com.wroclawroutes.routes.repository.UserRouteSavedRepository;
import com.wroclawroutes.routes.service.RouteService;
import com.wroclawroutes.routes.service.UserRouteRatingService;
import com.wroclawroutes.routes.service.UserSavedRoutesService;
import com.wroclawroutes.routes.service.mapper.UserActivityMapper;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserSavedRoutesServiceImpl implements UserSavedRoutesService {
    private final UserRouteSavedRepository userRouteSavedRepository;
   // private final UserRouteRatingService userRouteRatingService;
    private final RouteService routeService;
    private final UserService userService;
    private final UserRouteRatingService userRouteRatingService;
    private final UserActivityMapper userActivityMapper;
    @Override
    public ApiResponse save(UserDetailsImpl currentUser, Long routeId) {
        final Route route = routeService.findById(routeId);
        final User user = userService.getCurrentUser(currentUser);
        final boolean isRouteAlreadySaved = userRouteSavedRepository.existsByRouteAndUser(route, user);
        if(isRouteAlreadySaved){
            return new ApiResponse(Boolean.FALSE, "Route id: " + routeId + ", has been already saved by: " + currentUser.getEmail() );
        }

        final UserRouteSaved userRouteSaved = UserRouteSaved
                .builder()
                .route(route)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        userRouteSavedRepository.save(userRouteSaved);

        return new ApiResponse(Boolean.TRUE, "Route id: " + routeId + ", saved by: " + currentUser.getEmail() );
    }

    @Override
    public ApiResponse unSave(UserDetailsImpl currentUser, Long routeId) {
        final Route route = routeService.findById(routeId);
        final User user = userService.getCurrentUser(currentUser);
        final boolean isRouteAlreadySaved = userRouteSavedRepository.existsByRouteAndUser(route, user);
        if(isRouteAlreadySaved){
            UserRouteSaved userRouteSaved = userRouteSavedRepository.findByUserAndRoute(user, route).orElseThrow(() -> new NoSuchElementException("there is an error!!!!"));
            userRouteSavedRepository.delete(userRouteSaved);
            return new ApiResponse(Boolean.TRUE, "Route id: " + routeId + ", unsaved by: " + currentUser.getEmail() );
        } else{
            return new ApiResponse(Boolean.FALSE, "Route id: " + routeId + ", has not been already saved by: " + currentUser.getEmail() );
        }

    }

    @Override
    public List<UserRouteSaved> findAllByUserOrderByCreatedAt(User user) {
        return userRouteSavedRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<UserRouteSavedResponse> findUserRouteSavedResponseByUser(UserDetailsImpl currentUser) {
        final User user = userService.getCurrentUser(currentUser);
        final List<UserRouteSaved> userRoutesSaved = findAllByUserOrderByCreatedAt(user);
        return userRoutesSaved
                .stream()
                .map(
                        s->userActivityMapper
                        .mapUserRouteSavedResponse(
                                s,
                                userRouteRatingService.getAllUsersRouteRatingResponseByRoute(s.getRoute()),
                                userRouteRatingService.getGuideUsersRouteRatingResponseByRoute(s.getRoute())
                        )
                ).toList();
    }

    @Override
    public boolean isRouteSaved(UserDetailsImpl currentUser, Long routeId) {
        final Route route = routeService.findById(routeId);
        final User user = userService.getCurrentUser(currentUser);
        return userRouteSavedRepository.existsByRouteAndUser(route, user);
    }

    @Override
    public long numberOfRouteSavedTimes(Route route) {
        return route.getUsersSavedRoutes().size();
    }
}
