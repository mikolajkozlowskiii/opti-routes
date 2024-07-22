package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Path {
    @JsonProperty("distance")
    private int distanceInMeters;
    private double weight;
    @JsonProperty("time")
    private int timeInMiliSeconds;
    private int transfers;
    private String snapped_waypoints;
}
