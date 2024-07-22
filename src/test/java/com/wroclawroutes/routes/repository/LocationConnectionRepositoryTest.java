package com.wroclawroutes.routes.repository;


import static org.junit.jupiter.api.Assertions.*;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;


@DataJpaTest
public class LocationConnectionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LocationConnectionRepository locationConnectionRepository;

    @Test
    public void save_LocationConnection_entitySaved(){
        final Location startLocation = Location
                .builder()
                .name("Mango Mama")
                .address("Na Grobli 44")
                .longitude(51.105198755307575)
                .latitude(17.056662398142603)
                .build();

        final Location endLocation = Location
                .builder()
                .name("Pizza Si")
                .address("Sucha 45")
                .longitude(50.105028704327575)
                .latitude(17.059064499142603)
                .build();

        entityManager.persistAndFlush(startLocation);
        entityManager.persistAndFlush(endLocation);

        final LocationConnection locationConnection = LocationConnection
                .builder()
                .startLocation(startLocation)
                .endLocation(endLocation)
                .distanceInMeters(900)
                .timeOnFootInSec(240)
                .build();

        final LocationConnection actualLocationConnection = locationConnectionRepository.save(locationConnection);
        final LocationConnection expectedLocationConnection = entityManager.find(LocationConnection.class, locationConnection.getId());

        assertEquals(expectedLocationConnection, actualLocationConnection);
    }

    @Test
    public void save_TwoSameLocationConnectionCoordinates_thrownException(){
        final Location startLocation = Location
                .builder()
                .name("Mango Mama")
                .address("Na Grobli 44")
                .longitude(51.105198755307575)
                .latitude(17.056662398142603)
                .build();

        final Location endLocation = Location
                .builder()
                .name("Pizza Si")
                .address("Sucha 45")
                .longitude(50.105028704327575)
                .latitude(17.059064499142603)
                .build();

        entityManager.persistAndFlush(startLocation);
        entityManager.persistAndFlush(endLocation);

        final LocationConnection firstLocationConnection = LocationConnection
                .builder()
                .startLocation(startLocation)
                .endLocation(endLocation)
                .distanceInMeters(900)
                .timeOnFootInSec(240)
                .build();

        final LocationConnection secondLocationConnection = LocationConnection
                .builder()
                .startLocation(startLocation)
                .endLocation(endLocation)
                .distanceInMeters(12200)
                .timeOnFootInSec(360)
                .build();

        locationConnectionRepository.save(firstLocationConnection);
        assertThrows(DataIntegrityViolationException.class,
                () -> locationConnectionRepository.save(secondLocationConnection));

    }

    @Test
    @Sql(value = "classpath:/input-data/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findAllByStartLocation_LocationConnectionExistsInDB_entitiesFound(){
        final LocationConnection expectedLocationConnection = entityManager.find(LocationConnection.class, 1L);
        final LocationConnection actualLocationConnection = locationConnectionRepository.findById(1L).get();

        assertEquals(expectedLocationConnection, actualLocationConnection);
    }

    @Test
    @Sql(value = "classpath:/input-data/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_LocationConnectionExistsInDB_entityFound(){
        final Location startLocation = entityManager.find(Location.class, 2L);
        final List<LocationConnection> expectedLocationConnection = List.of(entityManager.find(LocationConnection.class, 3L));
        final List<LocationConnection> actualLocationConnection = locationConnectionRepository.findAllByStartLocation(startLocation);

        assertTrue(CollectionUtils.isEqualCollection(expectedLocationConnection, actualLocationConnection));
    }

    @Test
    @Sql(value = "classpath:/input-data/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findById_LocationConnectionsExistsInDB_entityFound(){
        final Location startLocation = entityManager.find(Location.class, 3L);
        final Location endLocation = entityManager.find(Location.class, 2L);
        final LocationConnection expectedLocationConnection = entityManager.find(LocationConnection.class, 4L);
        final LocationConnection actualLocationConnection =
                locationConnectionRepository.findByStartLocationAndEndLocation(startLocation, endLocation).get();

        assertEquals(expectedLocationConnection, actualLocationConnection);
    }
}
