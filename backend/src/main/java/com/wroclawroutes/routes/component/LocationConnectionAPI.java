package com.wroclawroutes.routes.component;

import com.wroclawroutes.routes.dto.LocationDTO;
import com.wroclawroutes.routes.dto.RouteParameters;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.entity.RouteStep;

import java.util.List;
import java.util.Set;

public interface LocationConnectionAPI {
    LocationConnection getLocationConnection(Location startPoint, Location endPoint);
    RouteParameters getRouteParameters(List<RouteStep> steps);
}
