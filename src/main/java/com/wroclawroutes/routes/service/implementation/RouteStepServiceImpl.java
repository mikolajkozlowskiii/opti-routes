package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import com.wroclawroutes.routes.repository.RouteStepRepository;
import com.wroclawroutes.routes.service.RouteStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RouteStepServiceImpl implements RouteStepService {
    private final RouteStepRepository routeStepRepository;

    @Override
    public RouteStep save(RouteStep step) {
        return routeStepRepository.save(step);
    }

    @Override
    public RouteStep findById(Long id) {
        return routeStepRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("RouteStep with id: %d not found.".formatted(id)));
    }

    @Override
    public List<RouteStep> findAllByRoute(Route route) {
        return routeStepRepository.findAllByRoute(route);
    }
}
