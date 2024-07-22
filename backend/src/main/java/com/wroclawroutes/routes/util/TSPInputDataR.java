package com.wroclawroutes.routes.util;

import com.wroclawroutes.routes.entity.Location;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TSPInputDataR {
    private Map<Integer, Location> locations;
    private long[][] distanceMatrix;
    private int depot;

    public Location getLocation(int index){
        Location location =  locations.get(index);
        return location;
    }


}
