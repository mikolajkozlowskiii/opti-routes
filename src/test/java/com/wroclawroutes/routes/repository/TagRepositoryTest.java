package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.*;

@DataJpaTest
public class TagRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TagRepository tagRepository;

    static Stream<Arguments> provideTagInputWithCountResult() {
        return Stream.of(
                arguments("HISTORICAL", 2),
                arguments("ROMANTIC", 2),
                arguments("PANORAMIC", 2),
                arguments("LOCALS", 0),
                arguments("BEAUTIFUL VIEW", 0),
                arguments("SPORTS", 2),
                arguments("NOT TAG NAME IN DB", 0),
                arguments("TOO LONG TAG NAME 123456789123456789123456789", 0)
        );
    }
    @ParameterizedTest
    @MethodSource("provideTagInputWithCountResult")
    @Sql(value = "classpath:/input-data/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void myTestMethod(String tagName, long expectedCount) {
        final long actualCount = tagRepository.countRoutesByTagName(tagName);
        assertEquals(expectedCount, actualCount);
    }

    @Test
    @Sql(value = "classpath:/input-data/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_TagIdExistsInDB_ReturnsRoute() {
        final Tag expectedTag = entityManager.find(Tag.class, 1L);
        final Tag actualTag = tagRepository.findById(1L).get();

        assertEquals(expectedTag, actualTag);
    }

    @Test
    @Sql(value = "classpath:/input-data/import-routes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/input-data/import-routes-tags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_TagIdDoesNotExistsInDB_ReturnEmptyOptional() {
        final Optional<Tag> actualTag = tagRepository.findById(9L);

        assertTrue(actualTag.isEmpty());
    }

}
