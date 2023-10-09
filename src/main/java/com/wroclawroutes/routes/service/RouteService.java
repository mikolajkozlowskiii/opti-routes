package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;

import java.util.List;
import java.util.Set;

public interface RouteService {
    Route save(Route route);
    void delete(Route route);
    Route findById(Long id);
    Route findByIdFetchStepsEagerly(Long id);
    List<Route> findAll();
    List<Route> findAllFetchTagsAndRatingsEagerly();
    List<Route> findAllByTagsContains(Tag tag);
    List<Route> findAllByTagContainsFetchRatingsAndTagsEagerly(Tag tag);
    List<Route> findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set<Tag> tag);
    List<Route> findAllByOnlyTags(Set<Tag> tags);
}