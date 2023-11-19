package com.wroclawroutes.routes.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

@DataJpaTest
public class RouteRepositoryTest {
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_RouteIdExistsInDB_ReturnsRoute() {
        final Route expectedRoute = entityManager.find(Route.class, 1L);
        final Route actualRoute = routeRepository.findById(1L).get();

        assertEquals(expectedRoute, actualRoute);
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findByIdFetchStepsEagerly_RouteIdExistsInDB_ReturnsRoute() {
        final Route expectedRoute = entityManager.find(Route.class, 1L);
        final Route actualRoute = routeRepository.findByIdFetchStepsEagerly(1L).get();

        assertEquals(expectedRoute, actualRoute);
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAll_RoutesExists_ReturnsRoutes() {
        final List<Route> expectedRoutes = Set.of(1, 2, 3, 4, 5)
                .stream()
                .map(s -> entityManager.find(Route.class, s))
                .toList();
        final List<Route> actualRoutes = routeRepository.findAll();

        assertTrue(CollectionUtils.isEqualCollection(expectedRoutes, actualRoutes));
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllFetchTagsAndRatingsEagerly_RoutesExists_ReturnsRoutes() {
        final List<Route> expectedRoutes = Set.of(1, 2, 3, 4, 5)
                .stream()
                .map(s -> entityManager.find(Route.class, s))
                .toList();
        final List<Route> actualRoutes = routeRepository.findAllFetchTagsAndRatingsEagerly();

        assertTrue(CollectionUtils.isEqualCollection(expectedRoutes, actualRoutes));
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByTagsContains_RoutesWithTags_ReturnsRoute() {
        final List<Route> expectedRoutes = Set.of(1, 2)
                .stream()
                .map(s -> entityManager.find(Route.class, s))
                .toList();

        final Tag tag = entityManager.find(Tag.class, 1L);
        final List<Route> actualRoutes = routeRepository.findAllByTagsContains(tag);

        assertTrue(CollectionUtils.isEqualCollection(expectedRoutes, actualRoutes));
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByTagContainsFetchRatingsAndTagsEagerly_RoutesWithTags_ReturnsRoute() {
        final List<Route> expectedRoutes = Set.of(1, 2)
                .stream()
                .map(s -> entityManager.find(Route.class, s))
                .toList();

        final Tag tag = entityManager.find(Tag.class, 1L);
        final List<Route> actualRoutes = routeRepository.findAllByTagContainsFetchRatingsAndTagsEagerly(tag);

        assertTrue(CollectionUtils.isEqualCollection(expectedRoutes, actualRoutes));
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByAnyTagsContainsFetchRatingsAndTagsEagerly_RoutesWithTags_ReturnsRoute() {
        final List<Route> expectedRoutes = Set.of(1, 2)
                .stream()
                .map(s -> entityManager.find(Route.class, s))
                .toList();

        final Tag firstTag = entityManager.find(Tag.class, 1L);
        final Tag secondTag = entityManager.find(Tag.class, 2L);
        final List<Route> actualRoutes =
                routeRepository.findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set.of(firstTag, secondTag));

        assertTrue(CollectionUtils.isEqualCollection(expectedRoutes, actualRoutes));
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-steps.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByTagsContainsFetchRatingsAndTagsEagerly_RoutesWithTags_ReturnsRoute() {
        final List<Route> expectedRoutes = Set.of(2)
                .stream()
                .map(s -> entityManager.find(Route.class, s))
                .toList();

        final Tag firstTag = entityManager.find(Tag.class, 1L);
        final Tag secondTag = entityManager.find(Tag.class, 2L);
        final List<Route> actualRoutes =
                routeRepository.findAllByOnlyTags(Set.of(firstTag, secondTag), 2);

        assertTrue(CollectionUtils.isEqualCollection(expectedRoutes, actualRoutes));
    }
}