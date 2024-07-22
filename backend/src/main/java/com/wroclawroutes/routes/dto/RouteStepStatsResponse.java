package com.wroclawroutes.routes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteStepStatsResponse implements Comparable<RouteStepStatsResponse>{
    private LocationStatResponse location;
    private Integer step;

    @Override
    public int compareTo(RouteStepStatsResponse o) {
        return this.step.compareTo(o.getStep());
    }
}
