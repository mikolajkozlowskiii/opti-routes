package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;
import com.wroclawroutes.routes.exceptions.RouteNotFoundException;
import com.wroclawroutes.routes.repository.RouteRepository;
import com.wroclawroutes.routes.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    @Override
    public Route save(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public void delete(Route route) {
        routeRepository.delete(route);
    }

    @Override
    public Route findById(Long id) {
        return routeRepository.findById(id).orElseThrow(() -> new RouteNotFoundException(id));
    }

    @Override
    public Route findByIdFetchStepsEagerly(Long id) {
        return routeRepository.findByIdFetchStepsEagerly(id).orElseThrow(() -> new RouteNotFoundException(id));
    }

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public List<Route> findAllFetchTagsAndRatingsEagerly() {
        return routeRepository.findAllFetchTagsAndRatingsEagerly();
    }

    @Override
    public List<Route> findAllByTagsContains(Tag tag) {
        return routeRepository.findAllByTagsContains(tag);
    }

    @Override
    public List<Route> findAllByTagContainsFetchRatingsAndTagsEagerly(Tag tag) {
        return routeRepository.findAllByTagContainsFetchRatingsAndTagsEagerly(tag);
    }

    @Override
    public List<Route> findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set<Tag> tags) {
        return routeRepository.findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(tags);
    }

    @Override
    public List<Route> findAllByOnlyTags(Set<Tag> tags) {
        final int tagCount = tags.size();
        return routeRepository.findAllByOnlyTags(tags, tagCount);
    }
}
