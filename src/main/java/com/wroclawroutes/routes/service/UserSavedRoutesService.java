package com.wroclawroutes.routes.service;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.UserRouteSavedResponse;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.UserRouteSaved;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.entities.User;

import java.util.List;

public interface UserSavedRoutesService {
    ApiResponse save(UserDetailsImpl currentUser, Long routeId);
    ApiResponse unSave(UserDetailsImpl currentUser, Long routeId);
    List<UserRouteSaved> findAllByUserOrderByCreatedAt(User user);
    List<UserRouteSavedResponse> findUserRouteSavedResponseByUser(UserDetailsImpl currentUser);
    boolean isRouteSaved(UserDetailsImpl currentUser, Long routeId);
    long numberOfRouteSavedTimes(Route route);
}
