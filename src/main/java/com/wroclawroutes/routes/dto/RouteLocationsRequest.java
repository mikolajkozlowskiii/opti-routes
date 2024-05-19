package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteLocationsRequest {
    @NotNull
    private Set<LocationDTO> locations;
    @NotNull
    private LocationDTO depot;
    @Max(15)
    @Min(0)
    @JsonProperty("limit_improving_heuristic_in_seconds")
    private int limitImprovingHeuristicInSeconds;
}
