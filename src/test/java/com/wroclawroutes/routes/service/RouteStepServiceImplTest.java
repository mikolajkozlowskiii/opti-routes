package com.wroclawroutes.routes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.RouteStep;
import com.wroclawroutes.routes.repository.RouteStepRepository;
import com.wroclawroutes.routes.service.implementation.RouteStepServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class RouteStepServiceImplTest {
    @InjectMocks
    private RouteStepServiceImpl routeStepService;
    @Mock
    private RouteStepRepository routeStepRepository;
    private RouteStep firstStep;
    private RouteStep secondStep;

    @BeforeEach
    void setUp(){
        firstStep = RouteStep
                .builder()
                .id(1L)
                .step(1)
                .location(
                        Location
                        .builder()
                        .name("Sky Tower")
                        .longitude(51.1234)
                        .latitude(12.21323)
                        .build()
                )
                .route(
                        Route
                        .builder()
                        .id(1L)
                        .description("Description")
                        .distanceInMeters(1200)
                        .timeInMilliseconds(400)
                        .createdAt(LocalDateTime.of(2023,10,10,12,30))
                        .isPublic(true)
                        .build()
                )
                .build();
        secondStep = RouteStep
                .builder()
                .id(2L)
                .step(2)
                .location(
                        Location
                        .builder()
                        .name("Hydropolis")
                        .longitude(52.1234)
                        .latitude(23.21323)
                        .build())
                .route(Route
                        .builder()
                        .id(2L)
                        .description("Description")
                        .distanceInMeters(3400)
                        .timeInMilliseconds(600)
                        .createdAt(LocalDateTime.of(2023,11,10,12,30))
                        .isPublic(true)
                        .build())
                .build();
    }

    @Test
    void save() {
        routeStepService.save(firstStep);

        verify(routeStepRepository).save(firstStep);
    }

    @Test
    void findById() {
        when(routeStepRepository.findById(anyLong())).thenReturn(Optional.of(firstStep));

        final RouteStep actualStep = routeStepService.findById(anyLong());

        assertEquals(firstStep, actualStep);
        verify(routeStepRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(routeStepRepository);
    }

    @Test
    void findAllByRoute() {
        final List<RouteStep> expectedSteps = List.of(firstStep, secondStep);
        when(routeStepRepository.findAllByRoute(any())).thenReturn(expectedSteps);

        final List<RouteStep> actualSteps = routeStepService.findAllByRoute(any());

        assertEquals(expectedSteps, actualSteps);
        verify(routeStepRepository, times(1)).findAllByRoute(any());
        verifyNoMoreInteractions(routeStepRepository);
    }
}