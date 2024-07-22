package com.wroclawroutes.routes.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RouteParameters {
    private int distanceInMeters;
    private int timeInMiliSeconds;
}
