package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.UserLocationLiked;
import com.wroclawroutes.users.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
public class UserLocationLikedRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserLocationLikedRepository userLocationLikedRepository;

    @Test
    @Sql(value = "classpath:/input-data/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-users-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-user-location-liked.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void ffindById_LikesExistsInDB_ReturnsUserLocationLikedEntity() {
        final UserLocationLiked expectedUserLocationLiked = entityManager.find(UserLocationLiked.class, 1L);
        final UserLocationLiked actualUserLocationLiked = userLocationLikedRepository.findById(1L).get();

        assertEquals(expectedUserLocationLiked, actualUserLocationLiked);
    }

    @Test
    @Sql(value = "classpath:/input-data/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-users-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-user-location-liked.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByUserOrderByCreatedAtAsc_TagIdExistsInDB_ReturnsRoute() {
        final User user = entityManager.find(User.class, 1L);
        final List<UserLocationLiked> expectedUserLocationLiked = List.of(1, 4, 3, 5, 2)
                .stream()
                .map(s -> entityManager.find(UserLocationLiked.class, s))
                .toList();
        final List<UserLocationLiked> actualUserLocationLiked = userLocationLikedRepository.findAllByUserOrderByCreatedAtAsc(user);

        assertEquals(expectedUserLocationLiked, actualUserLocationLiked);
    }

    @Test
    @Sql(value = "classpath:/input-data/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-users-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-user-location-liked.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByUserOrderByCreatedAtDesc_LikesExistsInDB_ReturnsUserLocationLikes() {
        final User user = entityManager.find(User.class, 1L);
        final List<UserLocationLiked> expectedUserLocationLiked = List.of(2, 5, 3, 4, 1)
                .stream()
                .map(s -> entityManager.find(UserLocationLiked.class, s))
                .toList();
        final List<UserLocationLiked> actualUserLocationLiked = userLocationLikedRepository.findAllByUserOrderByCreatedAtDesc(user);

        assertEquals(expectedUserLocationLiked, actualUserLocationLiked);
    }

    static Stream<Arguments> provideLocationIdInputWithLikesCountResult() {
        return Stream.of(
                arguments(1L, -1L),
                arguments(2L, 3L),
                arguments(3L, 0L),
                arguments(4L, -1L),
                arguments(5L, 1L),
                arguments(6L, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideLocationIdInputWithLikesCountResult")
    @Sql(value = "classpath:/input-data/import-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-users-roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-user-location-liked.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void countLikedLocation_EntitiesExistsInDB_ReturnsNumOfLocationLikes(Long inputLocationId, Long expectedLikesCount) {
        final Location location = entityManager.find(Location.class, inputLocationId);

        final Long actualLikesCount = userLocationLikedRepository.countLikedLocation(location);

        assertEquals(expectedLikesCount, actualLikesCount);
    }
}
