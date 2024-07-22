package com.wroclawroutes.routes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.wroclawroutes.routes.component.LocationConnectionAPI;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;
import com.wroclawroutes.routes.exceptions.RouteNotFoundException;
import com.wroclawroutes.routes.repository.RouteRepository;
import com.wroclawroutes.routes.service.implementation.RouteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {
    @InjectMocks
    private RouteServiceImpl routeService;
    @Mock
    private RouteRepository routeRepository;
    @Mock
    private RouteStepService routeStepService;
    @Mock
    private LocationConnectionAPI locationConnectionAPI;
    @Test
    void findByIdFetchAllRelationships_IdExistsInDB_ReturnsRoute() {
        final Route expectedRoute = Route
                .builder()
                .id(1L)
                .description("Description")
                .distanceInMeters(1200)
                .timeInMilliseconds(400)
                .createdAt(LocalDateTime.of(2023,10,10,12,30))
                .isPublic(true)
                .build();

        when(routeRepository.findByIdFetchAllRelationships(anyLong())).thenReturn(Optional.of(expectedRoute));

        final Route actualRoute = routeService.findByIdFetchAllRelationships(anyLong());

        assertEquals(expectedRoute, actualRoute);
        verify(routeRepository, times(1)).findByIdFetchAllRelationships(any());
        verifyNoMoreInteractions(routeRepository);
    }
    @Test
    void findByIdFetchAllRelationships_IdDoesntExistsInDB_ThrownException() {
        when(routeRepository.findByIdFetchAllRelationships(anyLong())).thenReturn(Optional.empty());

        assertThrows(RouteNotFoundException.class, () -> routeService.findByIdFetchAllRelationships(1L));
        verify(routeRepository, times(1)).findByIdFetchAllRelationships(any());
        verifyNoMoreInteractions(routeRepository);
    }



    @Test
    void findById_IdExistsInDB_ReturnsRoute() {
        final Route expectedRoute = Route
                .builder()
                .id(1L)
                .description("Description")
                .distanceInMeters(1200)
                .timeInMilliseconds(400)
                .createdAt(LocalDateTime.of(2023,10,10,12,30))
                .isPublic(true)
                .build();

        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoute));

        final Route actualRoute = routeService.findById(anyLong());

        assertEquals(expectedRoute, actualRoute);
        verify(routeRepository, times(1)).findById(any());
        verifyNoMoreInteractions(routeRepository);
    }

    @Test
    void findById_IdDoesntExistsInDB_ThrownException() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RouteNotFoundException.class, () -> routeService.findById(1L));
        verify(routeRepository, times(1)).findById(any());
        verifyNoMoreInteractions(routeRepository);
    }
    @Test
    void save() {
        final Route route = Route
                .builder()
                .id(1L)
                .description("Description")
                .distanceInMeters(1200)
                .timeInMilliseconds(400)
                .createdAt(LocalDateTime.of(2023,10,10,12,30))
                .isPublic(true)
                .build();

        routeService.save(route);

        verify(routeRepository, times(1)).save(route);
    }
    @Test
    void delete() {
        final Route route = Route
                .builder()
                .id(1L)
                .description("Description")
                .distanceInMeters(1200)
                .timeInMilliseconds(400)
                .createdAt(LocalDateTime.of(2023,10,10,12,30))
                .isPublic(true)
                .build();

        routeService.delete(route);

        verify(routeRepository, times(1)).delete(route);
    }



    @Test
    void findByIdFetchStepsEagerly() {
        final Route expectedRoute = Route
                .builder()
                .id(1L)
                .description("Description")
                .distanceInMeters(1200)
                .timeInMilliseconds(400)
                .createdAt(LocalDateTime.of(2023,10,10,12,30))
                .isPublic(true)
                .build();

        when(routeRepository.findByIdFetchStepsWithLocationsEagerly(anyLong())).thenReturn(Optional.of(expectedRoute));

        final Route actualRoute = routeService.findByIdFetchStepsEagerly(anyLong());

        assertEquals(expectedRoute, actualRoute);
        verify(routeRepository, times(1)).findByIdFetchStepsWithLocationsEagerly(any());
        verifyNoMoreInteractions(routeRepository);
    }

    @Test
    void findAll() {
        final List<Route> expectedRoutes = List.of(
                Route
                        .builder()
                        .id(1L)
                        .description("Description")
                        .distanceInMeters(1200)
                        .timeInMilliseconds(400)
                        .createdAt(LocalDateTime.of(2023,10,10,12,30))
                        .isPublic(true)
                        .build(),
                Route
                        .builder()
                        .id(2L)
                        .description("Desc")
                        .distanceInMeters(3000)
                        .timeInMilliseconds(800)
                        .createdAt(LocalDateTime.of(2023,11,10,12,30))
                        .isPublic(false)
                        .build()
        );
        when(routeRepository.findAll()).thenReturn(expectedRoutes);

        final List<Route> actualRoutes = routeService.findAll();

        assertEquals(expectedRoutes, actualRoutes);
        verify(routeRepository, times(1)).findAll();
        verifyNoMoreInteractions(routeRepository);
    }

    @Test
    void findAllFetchTagsAndRatingsEagerly() {
        final List<Route> expectedRoutes = List.of(
                Route
                        .builder()
                        .id(1L)
                        .description("Description")
                        .distanceInMeters(1200)
                        .timeInMilliseconds(400)
                        .createdAt(LocalDateTime.of(2023,10,10,12,30))
                        .isPublic(true)
                        .build(),
                Route
                        .builder()
                        .id(2L)
                        .description("Desc")
                        .distanceInMeters(3000)
                        .timeInMilliseconds(800)
                        .createdAt(LocalDateTime.of(2023,11,10,12,30))
                        .isPublic(false)
                        .build()
        );
        when(routeRepository.findAllFetchTagsAndRatingsEagerly()).thenReturn(expectedRoutes);

        final List<Route> actualRoutes = routeService.findAllFetchTagsAndRatingsEagerly();

        assertEquals(expectedRoutes, actualRoutes);
        verify(routeRepository, times(1)).findAllFetchTagsAndRatingsEagerly();
        verifyNoMoreInteractions(routeRepository);
    }

    @Test
    void findAllByTagsContains() {
        final Tag tag = Tag
                .builder()
                .id(1L)
                .name("PANORAMIC")
                .build();
        final List<Route> expectedRoutes = List.of(
                Route
                        .builder()
                        .id(1L)
                        .description("Description")
                        .distanceInMeters(1200)
                        .timeInMilliseconds(400)
                        .createdAt(LocalDateTime.of(2023,10,10,12,30))
                        .isPublic(true)
                        .tags(Set.of(tag))
                        .build(),
                Route
                        .builder()
                        .id(2L)
                        .description("Desc")
                        .distanceInMeters(3000)
                        .timeInMilliseconds(800)
                        .createdAt(LocalDateTime.of(2023,11,10,12,30))
                        .isPublic(false)
                        .tags(Set.of(tag))
                        .build()
        );
        when(routeRepository.findAllByTagsContains(tag)).thenReturn(expectedRoutes);

        final List<Route> actualRoutes = routeService.findAllByTagsContains(tag);

        assertEquals(expectedRoutes, actualRoutes);
        verify(routeRepository, times(1)).findAllByTagsContains(tag);
        verifyNoMoreInteractions(routeRepository);
    }

    @Test
    void findAllByTagContainsFetchRatingsAndTagsEagerly() {
        final Tag tag = Tag
                .builder()
                .id(1L)
                .name("PANORAMIC")
                .build();
        final List<Route> expectedRoutes = List.of(
                Route
                        .builder()
                        .id(1L)
                        .description("Description")
                        .distanceInMeters(1200)
                        .timeInMilliseconds(400)
                        .createdAt(LocalDateTime.of(2023,10,10,12,30))
                        .isPublic(true)
                        .tags(Set.of(tag))
                        .build(),
                Route
                        .builder()
                        .id(2L)
                        .description("Desc")
                        .distanceInMeters(3000)
                        .timeInMilliseconds(800)
                        .createdAt(LocalDateTime.of(2023,11,10,12,30))
                        .isPublic(false)
                        .tags(Set.of(tag))
                        .build()
        );
        when(routeRepository.findAllByTagContainsFetchRatingsAndTagsEagerly(tag)).thenReturn(expectedRoutes);

        final List<Route> actualRoutes = routeService.findAllByTagContainsFetchRatingsAndTagsEagerly(tag);

        assertEquals(expectedRoutes, actualRoutes);
        verify(routeRepository, times(1)).findAllByTagContainsFetchRatingsAndTagsEagerly(tag);
        verifyNoMoreInteractions(routeRepository);
    }

    @Test
    void findAllByAnyTagsContainsFetchRatingsAndTagsEagerly() {
        final Tag tag = Tag
                .builder()
                .id(1L)
                .name("PANORAMIC")
                .build();
        final List<Route> expectedRoutes = List.of(
                Route
                        .builder()
                        .id(1L)
                        .description("Description")
                        .distanceInMeters(1200)
                        .timeInMilliseconds(400)
                        .createdAt(LocalDateTime.of(2023,10,10,12,30))
                        .isPublic(true)
                        .tags(Set.of(tag))
                        .build(),
                Route
                        .builder()
                        .id(2L)
                        .description("Desc")
                        .distanceInMeters(3000)
                        .timeInMilliseconds(800)
                        .createdAt(LocalDateTime.of(2023,11,10,12,30))
                        .isPublic(false)
                        .tags(Set.of(tag))
                        .build()
        );
        when(routeRepository.findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set.of(tag))).thenReturn(expectedRoutes);

        final List<Route> actualRoutes = routeService.findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set.of(tag));

        assertEquals(expectedRoutes, actualRoutes);
        verify(routeRepository, times(1)).findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(Set.of(tag));
        verifyNoMoreInteractions(routeRepository);
    }

    @Test
    void findAllByOnlyTags() {
        final Tag tag = Tag
                .builder()
                .id(1L)
                .name("PANORAMIC")
                .build();
        final List<Route> expectedRoutes = List.of(
                Route
                        .builder()
                        .id(1L)
                        .description("Description")
                        .distanceInMeters(1200)
                        .timeInMilliseconds(400)
                        .createdAt(LocalDateTime.of(2023,10,10,12,30))
                        .isPublic(true)
                        .tags(Set.of(tag))
                        .build(),
                Route
                        .builder()
                        .id(2L)
                        .description("Desc")
                        .distanceInMeters(3000)
                        .timeInMilliseconds(800)
                        .createdAt(LocalDateTime.of(2023,11,10,12,30))
                        .isPublic(false)
                        .tags(Set.of(tag))
                        .build()
        );
        when(routeRepository.findAllByOnlyTags(Set.of(tag), 1)).thenReturn(expectedRoutes);

        final List<Route> actualRoutes = routeService.findAllByOnlyTags(Set.of(tag));

        assertEquals(expectedRoutes, actualRoutes);
        verify(routeRepository, times(1)).findAllByOnlyTags(Set.of(tag), 1);
        verifyNoMoreInteractions(routeRepository);
    }
}