package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Point {
    @JsonProperty("lat")
    private Double lattitude;
    @JsonProperty("lng")
    private Double longtitude;
}
