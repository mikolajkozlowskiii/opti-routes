package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.dto.RouteRequest;
import com.wroclawroutes.routes.dto.RouteResponse;
import com.wroclawroutes.routes.dto.RouteStatsResponse;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;

import java.util.List;
import java.util.Set;

public interface RouteResponseService {
    RouteResponse createRouteWithOptimization(RouteRequest route, UserDetailsImpl userDetails);
    RouteResponse save(RouteRequest route, UserDetailsImpl userDetails);
    void deleteById(Long id);
    void delete(Route route, UserDetailsImpl userDetails);
    RouteResponse findById(Long id);
    RouteStatsResponse findRouteStatsById(Long id);

    Route findByIdFetchStepsEagerly(Long id);
    RouteResponse findResponseById(Long id);
    RouteStatsResponse findStatsResponseById(Long id);
    List<RouteStatsResponse> findAllStatsResponse();
    List<RouteStatsResponse> findAllStatsResponseByEmail(String email);
    List<RouteStatsResponse> findAllStatsResponseByAuthorCurrentUser(UserDetailsImpl currentUser);
    List<Route> findAll();
    List<Route> findAllFetchTagsAndRatingsEagerly();
    List<Route> findAllByTagsContains(Tag tag);
    List<Route> findAllByTagContainsFetchRatingsAndTagsEagerly(Tag tag);
    List<Route> findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set<Tag> tag);
    List<Route> findAllByOnlyTags(Set<Tag> tags);
}
