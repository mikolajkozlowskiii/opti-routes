package com.wroclawroutes.routes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationStatResponse {
    private long id;
    private LocationDTO locationInfo;
    private Integer numOfLikes;
}
