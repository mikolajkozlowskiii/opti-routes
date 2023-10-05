package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RouteStepRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RouteStepRepository routeStepRepository;
    @Test
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_RouteIdExistsInDB_ReturnsRoute() {
        final RouteStep expectedRouteStep = entityManager.find(RouteStep.class, 1L);
        final RouteStep actualRouteStep = routeStepRepository.findById(1L).get();

        assertEquals(expectedRouteStep, actualRouteStep);
    }

    @Test
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByRoute_RouteIdExistsInDB_ReturnsRoute() {
        final List<RouteStep> expectedRouteSteps = Set.of(1, 2, 3, 4)
                .stream()
                .map(s -> entityManager.find(RouteStep.class, s))
                .toList();
        final Route route = entityManager.find(Route.class, 1L);
        final List<RouteStep> actualRouteSteps = routeStepRepository.findAllByRoute(route);

        assertEquals(expectedRouteSteps, actualRouteSteps);
    }
}
