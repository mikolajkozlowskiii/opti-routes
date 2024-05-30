package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wroclawroutes.routes.entity.RouteStep;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OptimizationGain {
    private Integer timeInMilliSecondsGain;
    private Integer distanceInMetersGain;
}
