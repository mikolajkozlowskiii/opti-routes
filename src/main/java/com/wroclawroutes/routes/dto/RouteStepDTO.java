package com.wroclawroutes.routes.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class RouteStepDTO implements Comparable<RouteStepDTO>{
    private LocationDTO location;
    private Integer step;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteStepDTO that = (RouteStepDTO) o;
        return Objects.equals(step, that.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step);
    }

    @Override
    public int compareTo(RouteStepDTO routeStepDTO) {
        return Integer.compare(getStep(), (routeStepDTO.getStep()));
    }
}
