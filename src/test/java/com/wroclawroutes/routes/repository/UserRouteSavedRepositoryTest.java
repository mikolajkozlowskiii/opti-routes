package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.routes.entity.UserRouteSaved;
import com.wroclawroutes.users.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRouteSavedRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRouteSavedRepository userRouteSavedRepository;

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-users-routes-saved.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_EntitiesExistsInDB_ReturnsUserRouteRatingEntity() {
        final UserRouteSaved expectedUserRouteSaved= entityManager.find(UserRouteSaved.class, 1L);
        final UserRouteSaved actualUserRouteSaved= userRouteSavedRepository.findById(1L).get();

        assertEquals(expectedUserRouteSaved, actualUserRouteSaved);
    }

    @Test
    @Sql(value = "classpath:/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/import-users-routes-saved.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByUserOrderByCreatedAt_EntitiesExistsInDB_ReturnsUserRouteRatingEntity() {
        final User user = entityManager.find(User.class, 1L);
        final List<UserRouteSaved> expectedUserRoutesSaved = List.of(1, 6, 4)
                .stream()
                .map(s -> entityManager.find(UserRouteSaved.class, s))
                .toList();

        final List<UserRouteSaved> actualUserRoutesSaved= userRouteSavedRepository.findAllByUserOrderByCreatedAt(user);

        assertEquals(expectedUserRoutesSaved, actualUserRoutesSaved);
    }
}
