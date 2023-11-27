package com.wroclawroutes.routes.dto;

import com.wroclawroutes.routes.entity.Location;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteStepResponse {
    private LocationDTO location;
    private Integer step;
}
