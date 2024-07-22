package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteStepRepository extends JpaRepository<RouteStep, Long> {
    Optional<RouteStep> findById(Long id);
    List<RouteStep> findAllByRoute(Route route);
}
