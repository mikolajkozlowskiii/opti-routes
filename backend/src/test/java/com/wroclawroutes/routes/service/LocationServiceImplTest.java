package com.wroclawroutes.routes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.repository.LocationRepository;
import com.wroclawroutes.routes.service.implementation.LocationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @InjectMocks
    private LocationServiceImpl locationService;
    @Mock
    private LocationRepository locationRepository;

    @Test
    void save() {
        final Location location = Location
                .builder()
                .name("Sky Tower")
                .longitude(51.1234)
                .latitude(12.21323)
                .build();

        locationService.save(location);

        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void delete() {
        final Location location = Location
                .builder()
                .name("Sky Tower")
                .longitude(51.1234)
                .latitude(12.21323)
                .build();

        locationService.delete(location);

        verify(locationRepository, times(1)).delete(location);
    }

    @Test
    void findById() {
        final Location expectedLocation = Location
                .builder()
                .id(1L)
                .name("Sky Tower")
                .longitude(51.1234)
                .latitude(12.21323)
                .build();
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(expectedLocation));

        final Location actualLocation = locationService.findById(1L);

        assertEquals(expectedLocation, actualLocation);
        verify(locationRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void findByName() {
        final Location expectedLocation = Location
                .builder()
                .id(1L)
                .name("Sky Tower")
                .longitude(51.1234)
                .latitude(12.21323)
                .build();
        when(locationRepository.findByName("Sky Tower")).thenReturn(Optional.of(expectedLocation));

        final Location actualLocation = locationService.findByName("Sky Tower");

        assertEquals(expectedLocation, actualLocation);
        verify(locationRepository, times(1)).findByName("Sky Tower");
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void findByLatitudeAndLongitude() {
        final Location expectedLocation = Location
                .builder()
                .id(1L)
                .name("Sky Tower")
                .longitude(51.1234)
                .latitude(12.21323)
                .build();
        when(locationRepository.findByName("Sky Tower")).thenReturn(Optional.of(expectedLocation));

        final Location actualLocation = locationService.findByName("Sky Tower");

        assertEquals(expectedLocation, actualLocation);
        verify(locationRepository, times(1)).findByName("Sky Tower");
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void existsByLatitudeAndLongitude_EntityExistsInDB_ReturnsTrue() {
        when(locationRepository.existsByLatitudeAndLongitude(anyDouble(), anyDouble())).thenReturn(true);

        assertTrue(locationService.existsByLatitudeAndLongitude(anyDouble(), anyDouble()));
        verify(locationRepository, times(1)).existsByLatitudeAndLongitude(anyDouble(), anyDouble());
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void existsByLatitudeAndLongitude_EntityDoesNotExistsInDB_ReturnsTrue() {
        when(locationRepository.existsByLatitudeAndLongitude(anyDouble(), anyDouble())).thenReturn(false);

        assertFalse(locationService.existsByLatitudeAndLongitude(anyDouble(), anyDouble()));
        verify(locationRepository, times(1)).existsByLatitudeAndLongitude(anyDouble(), anyDouble());
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void findAll() {
        final List<Location> expectedLocations = List.of(
                Location
                .builder()
                .id(1L)
                .name("Sky Tower")
                .longitude(51.1234)
                .latitude(12.21323)
                .build(),
                Location
                .builder()
                .id(2L)
                .name("Arkady Capitol")
                .longitude(52.1234)
                .latitude(11.21323)
                .build()
        );
        when(locationRepository.findAll()).thenReturn(expectedLocations);

        final List<Location> actualLocations = locationService.findAll();

        assertEquals(expectedLocations, actualLocations);
        verify(locationRepository, times(1)).findAll();
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void findAllFetchOutogingConnectionEagerly() {
        final List<Location> expectedLocations = List.of(
                Location
                        .builder()
                        .id(1L)
                        .name("Sky Tower")
                        .longitude(51.1234)
                        .latitude(12.21323)
                        .build(),
                Location
                        .builder()
                        .id(2L)
                        .name("Arkady Capitol")
                        .longitude(52.1234)
                        .latitude(11.21323)
                        .build()
        );
        when(locationRepository.findAllFetchOutogingConnectionEagerly()).thenReturn(expectedLocations);

        final List<Location> actualLocations = locationService.findAllFetchOutogingConnectionEagerly();

        assertEquals(expectedLocations, actualLocations);
        verify(locationRepository, times(1)).findAllFetchOutogingConnectionEagerly();
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void findAllFetchLikedByUsersLocationEagerly() {
        final List<Location> expectedLocations = List.of(
                Location
                        .builder()
                        .id(1L)
                        .name("Sky Tower")
                        .longitude(51.1234)
                        .latitude(12.21323)
                        .build(),
                Location
                        .builder()
                        .id(2L)
                        .name("Arkady Capitol")
                        .longitude(52.1234)
                        .latitude(11.21323)
                        .build()
        );
        when(locationRepository.findAllFetchLikedByUsersLocationEagerly()).thenReturn(expectedLocations);

        final List<Location> actualLocations = locationService.findAllFetchLikedByUsersLocationEagerly();

        assertEquals(expectedLocations, actualLocations);
        verify(locationRepository, times(1)).findAllFetchLikedByUsersLocationEagerly();
        verifyNoMoreInteractions(locationRepository);
    }
}
