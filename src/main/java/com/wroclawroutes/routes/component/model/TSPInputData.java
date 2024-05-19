package com.wroclawroutes.routes.component.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TSPInputData {
    private long[][] distanceMatrix;
    private int indexOfDepot;
    private int limitImprovingHeuristicInSeconds;
}

