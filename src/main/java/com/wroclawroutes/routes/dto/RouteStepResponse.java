package com.wroclawroutes.routes.dto;

import com.wroclawroutes.routes.entity.Location;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteStepResponse {
    private LocationResponse location;
    private Integer step;
}
