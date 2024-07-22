package com.wroclawroutes.routes.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationResponse {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}
