package com.wroclawroutes.routes.dto;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OptimizedStepsRequest {
    private Location depot;
    private Set<LocationConnection> locationConnections;
}
