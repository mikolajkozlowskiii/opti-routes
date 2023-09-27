package com.wroclawroutes.routes.entity;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class LocationEntityTest {
    @Autowired
    private TestEntityManager entityManager;
    private Location location;

    @BeforeEach
    public void setUp(){
        location = Location
                .builder()
                .name("Sky Tower")//
                .address("Powstańców Śląskich 95")
                .longitude(51.09557216113358)
                .latitude(17.019321302937936)
                .build();
    }

    @Test
    public void saveValidLocation_entitySaved(){
        final Location savedLocation = entityManager.persistAndFlush(location);
        System.out.println(savedLocation);
        assertTrue(savedLocation.equals(location));
    }

    @Test
    public void saveLocationWithNullCoordinates_exceptionThrown(){
        location.setLatitude(null);
        location.setAddress(null);
        assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(location));
    }

    @Test
    public void saveUniqueLocations_entitiesSaved(){
        final Location secondLocation = Location
                .builder()
                .name("Hydropolis")
                .address("Na Grobli 17")
                .longitude(51.105128704307575)
                .latitude(17.056662398142603)
                .build();
        final Location savedLocation = entityManager.persistAndFlush(location);
        final Location secondSavedLocation = entityManager.persistAndFlush(secondLocation);

        assertTrue(savedLocation.equals(location));
        assertTrue(secondSavedLocation.equals(secondLocation));
    }

    @Test
    public void saveNotUniqueLocations_exceptionThrown(){
        final Location secondLocation = Location
                .builder()
                .name(location.getName())
                .address(location.getAddress())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build();

        entityManager.persistAndFlush(location);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(secondLocation));

    }
}
