package com.wroclawroutes.routes.component.model;



import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class TSPOutputData {
    private long value;
    private List<Integer> orderedIndexes;
}
