package com.wroclawroutes.routes.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashSet;

@DataJpaTest
public class LocationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LocationRepository locationRepository;
    private Location location;
    @BeforeEach
    public void setUp(){
        location = Location
                .builder()
                .name("Sky Tower")
                .address("Powstańców Śląskich 95")
                .longitude(51.09557216113358)
                .latitude(17.019321302937936)
                .outgoingConnections(new HashSet<>())
                .build();
    }

    @Test
    public void findById_LocationIdExistsInDB_ReturnsLocation(){
        entityManager.persistAndFlush(location);

        final Location actualLocation = locationRepository.findById(location.getId()).get();
        assertEquals(location, actualLocation);
    }

    @Test
    public void findById_LocationWithOutgoingConnectionsExistsInDB_ReturnsLocation(){
        final Location secondLocation = Location
                .builder()
                .name("Hydropolis")
                .address("Na Grobli 17")
                .longitude(51.105128704307575)
                .latitude(17.056662398142603)
                .build();

        final Location thirdLocation = Location
                .builder()
                .name("Politechnika Wrocławska")
                .address("Grabiszyńska 54")
                .longitude(50.105128704307575)
                .latitude(17.059664398142603)
                .build();

        final LocationConnection firstLocationConnection = LocationConnection
                .builder()
                .startLocation(location)
                .endLocation(secondLocation)
                .distanceInMeters(100)
                .timeOnFootInSec(120)
                .build();

        final LocationConnection secondLocationConnection = LocationConnection
                .builder()
                .startLocation(location)
                .endLocation(thirdLocation)
                .distanceInMeters(900)
                .timeOnFootInSec(240)
                .build();

        entityManager.persistAndFlush(location);
        entityManager.persistAndFlush(secondLocation);
        entityManager.persistAndFlush(thirdLocation);
        location.addOutgoingConnection(firstLocationConnection);
        location.addOutgoingConnection(secondLocationConnection);
        entityManager.persistAndFlush(location);

        final Location actualLocation = locationRepository.findById(location.getId()).get();
        assertEquals(location, actualLocation);
    }

    @Test
    public void save_LocationEntity_LocationSaved(){
        final Location expectedLocation = locationRepository.save(location);

        final Location actualLocation = entityManager.find(Location.class, expectedLocation.getId());

        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void save_LocationWithLocationConnections_LocationSaved(){
        final Location secondLocation = Location
                .builder()
                .name("Hydropolis")
                .address("Na Grobli 17")
                .longitude(51.105128704307575)
                .latitude(17.056662398142603)
                .build();

        final Location thirdLocation = Location
                .builder()
                .name("Politechnika Wrocławska")
                .address("Grabiszyńska 54")
                .longitude(50.105128704307575)
                .latitude(17.059664398142603)
                .build();

        final LocationConnection firstLocationConnection = LocationConnection
                .builder()
                .startLocation(location)
                .endLocation(secondLocation)
                .distanceInMeters(100)
                .timeOnFootInSec(120)
                .build();

        final LocationConnection secondLocationConnection = LocationConnection
                .builder()
                .startLocation(location)
                .endLocation(thirdLocation)
                .distanceInMeters(900)
                .timeOnFootInSec(240)
                .build();

        locationRepository.save(secondLocation);
        locationRepository.save(thirdLocation);

        location.addOutgoingConnection(firstLocationConnection);
        location.addOutgoingConnection(secondLocationConnection);
        final Location actualLocation = locationRepository.save(location);

        assertEquals(location, actualLocation);
    }

    @Test
    @Sql(value = "classpath:/import-locations-connections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void saveLocationWithRemovedLocationConnections_LocationSaved(){
        final Location location = locationRepository.findById(1L).get();
        final LocationConnection locationConnectionToBeRemoved =
                entityManager.find(LocationConnection.class, 1L);
        final LocationConnection locationConnectionToNotBeRemoved =
                entityManager.find(LocationConnection.class, 2L);

        location.removeOutgoingConnection(locationConnectionToBeRemoved);
        final Location actualLocation = locationRepository.save(location);

        assertTrue(actualLocation.getOutgoingConnections().contains(locationConnectionToNotBeRemoved));
        assertFalse(actualLocation.getOutgoingConnections().contains(locationConnectionToBeRemoved));
    }
}
