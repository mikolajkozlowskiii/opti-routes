package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.users.services.components.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class RouteMapper {
    private final TagMapper tagMapper;
    private final RouteStepMapper routeStepMapper;
    private final UserMapper userMapper;

    public RouteResponse mapToRouteResponse(Route route){
        final List<RouteStepDTO> routeStepDTOs = new ArrayList<>(
                route.getSteps()
                        .stream()
                        .map(routeStepMapper::map)
                        .sorted()
                        .toList()
        );

        final List<TagDTO> tagDTOs = new ArrayList<>(
                route.getTags()
                        .stream()
                        .map(tagMapper::map)
                        .sorted()
                        .toList()
        );

        return RouteResponse
                .builder()
                .id(route.getId())
                .description(route.getDescription())
                .createdAt(route.getCreatedAt())
                .distanceInMeters(route.getDistanceInMeters())
                .timeInMiliSeconds(route.getTimeInMilliseconds())
                .isPublic(route.getIsPublic())
                .user(userMapper.map(route.getUser()))
                .steps(routeStepDTOs)
                .tags(tagDTOs)
                .build();
    }

    public Route mapToRouteResponse(RouteRequest routeRequest){
        return Route.builder()
                .description(routeRequest.getDescription())
                .isPublic(routeRequest.isPublic())
                .createdAt(LocalDateTime.now())
                .steps(new HashSet<>())
                .build();
    }

    public RouteStatsResponse map(Route route){
        final List<RouteStepDTO> routeStepDTOs = new ArrayList<>(route.getSteps().stream().map(routeStepMapper::map).toList());
        Collections.sort(routeStepDTOs);

        final List<TagDTO> tagDTOs = new ArrayList<>(route.getTags().stream().map(tagMapper::map).toList());
        Collections.sort(tagDTOs);
        return RouteStatsResponse
                .builder()
                .build();
    }
}
