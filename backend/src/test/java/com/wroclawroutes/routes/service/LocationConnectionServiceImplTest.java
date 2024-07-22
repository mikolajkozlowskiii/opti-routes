package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.repository.LocationConnectionRepository;
import com.wroclawroutes.routes.repository.LocationRepository;
import com.wroclawroutes.routes.service.implementation.LocationConnectionServiceImpl;
import com.wroclawroutes.routes.service.implementation.LocationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)

class LocationConnectionServiceImplTest {
    @InjectMocks
    private LocationConnectionServiceImpl locationConnectionService;
    @Mock
    private LocationConnectionRepository locationConnectionRepository;

    @Test
    void findById() {
        final LocationConnection expectedLocationConnection = LocationConnection
                .builder()
                .id(1L)
                .startLocation(
                        Location
                        .builder()
                        .id(1L)
                        .name("Sky Tower")
                        .longitude(51.1234)
                        .latitude(12.21323)
                        .build()
                )
                .endLocation(
                        Location
                                .builder()
                                .id(2L)
                                .name("Arkady Capitol")
                                .longitude(52.1234)
                                .latitude(19.21323)
                                .build()
                )
                .distanceInMeters(1200)
                .timeOnFootInSec(360)
                .build();
        when(locationConnectionRepository.findById(anyLong())).thenReturn(Optional.of(expectedLocationConnection));

        final LocationConnection actualLocationConnection = locationConnectionService.findById(1L);

        assertEquals(expectedLocationConnection, actualLocationConnection);
        verify(locationConnectionRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(locationConnectionRepository);
    }

    @Test
    void findAllByStartLocation() {
        final List<LocationConnection> expectedLocationConnections = List.of(
                LocationConnection
                .builder()
                .id(1L)
                .startLocation(
                        Location
                                .builder()
                                .id(1L)
                                .name("Sky Tower")
                                .longitude(51.1234)
                                .latitude(12.21323)
                                .build()
                )
                .endLocation(
                        Location
                                .builder()
                                .id(2L)
                                .name("Arkady Capitol")
                                .longitude(52.1234)
                                .latitude(19.21323)
                                .build()
                )
                .distanceInMeters(1200)
                .timeOnFootInSec(360)
                .build(),

                LocationConnection
                        .builder()
                        .id(2L)
                        .startLocation(
                                Location
                                        .builder()
                                        .id(1L)
                                        .name("Sky Tower")
                                        .longitude(51.1234)
                                        .latitude(12.21323)
                                        .build()
                        )
                        .endLocation(
                                Location
                                        .builder()
                                        .id(3L)
                                        .name("Hydropolis")
                                        .longitude(56.1234)
                                        .latitude(29.21323)
                                        .build()
                        )
                        .distanceInMeters(2500)
                        .timeOnFootInSec(720)
                        .build()
                );
        when(locationConnectionRepository.findAllByStartLocation(any())).thenReturn(expectedLocationConnections);

        final List<LocationConnection> actualLocationConnections = locationConnectionService.findAllByStartLocation(any());

        assertEquals(expectedLocationConnections, actualLocationConnections);
        verify(locationConnectionRepository, times(1)).findAllByStartLocation(any());
        verifyNoMoreInteractions(locationConnectionRepository);
    }

    @Test
    void findByStartLocationAndEndLocation() {
        final LocationConnection expectedLocationConnection = LocationConnection
                .builder()
                .id(1L)
                .startLocation(
                        Location
                                .builder()
                                .id(1L)
                                .name("Sky Tower")
                                .longitude(51.1234)
                                .latitude(12.21323)
                                .build()
                )
                .endLocation(
                        Location
                                .builder()
                                .id(2L)
                                .name("Arkady Capitol")
                                .longitude(52.1234)
                                .latitude(19.21323)
                                .build()
                )
                .distanceInMeters(1200)
                .timeOnFootInSec(360)
                .build();
        when(locationConnectionRepository.findByStartLocationAndEndLocation(any(), any())).thenReturn(Optional.of(expectedLocationConnection));

        final LocationConnection actualLocationConnection = locationConnectionService.findByStartLocationAndEndLocation(any(), any());

        assertEquals(expectedLocationConnection, actualLocationConnection);
        verify(locationConnectionRepository, times(1)).findByStartLocationAndEndLocation(any(), any());
        verifyNoMoreInteractions(locationConnectionRepository);
    }
}