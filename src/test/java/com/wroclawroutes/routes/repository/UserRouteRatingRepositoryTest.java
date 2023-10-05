package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.users.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRouteRatingRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRouteRatingRepository userRouteRatingRepository;
    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-users-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_RatingsExistsInDB_ReturnsUserRouteRatingEntity() {
        final UserRouteRating expectedUserRouteRating= entityManager.find(UserRouteRating.class, 1L);
        final UserRouteRating actualUserRouteRating= userRouteRatingRepository.findById(1L).get();

        assertEquals(expectedUserRouteRating, actualUserRouteRating);
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-users-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByUserOrderByCreatedAtDesc_RatingsExistsInDB_ReturnsUserRouteRatingEntity() {
        final User user = entityManager.find(User.class, 1L);
        final List<UserRouteRating> expectedUserRouteRatings = List.of(4, 6, 1)
                .stream()
                .map(s -> entityManager.find(UserRouteRating.class, s))
                .toList();

        final List<UserRouteRating> actualUserRouteRatings  =
                userRouteRatingRepository.findAllByUserOrderByCreatedAtDesc(user);

        assertEquals(expectedUserRouteRatings, actualUserRouteRatings);
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-users-routes-ratings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByRoute_RatingsExistsInDB_ReturnsUserRouteRatingEntity() {
        final Route route = entityManager.find(Route.class, 1L);
        final List<UserRouteRating> expectedUserRouteRatings = List.of(1, 2, 3)
                .stream()
                .map(s -> entityManager.find(UserRouteRating.class, s))
                .toList();

        final List<UserRouteRating> actualUserRouteRatings  =
                userRouteRatingRepository.findAllByRoute(route);

        assertEquals(expectedUserRouteRatings, actualUserRouteRatings);
    }
}
