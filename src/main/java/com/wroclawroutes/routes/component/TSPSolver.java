package com.wroclawroutes.routes.component;

import com.wroclawroutes.routes.component.model.TSPOutputData;
import com.wroclawroutes.routes.component.model.TSPInputData;

public interface TSPSolver {
    TSPOutputData solve(TSPInputData inputData);
}


