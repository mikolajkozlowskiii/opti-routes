package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wroclawroutes.users.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class UserRouteSavedResponse {
    @JsonProperty("userWhoSaved")
    private UserResponse user;
    @JsonProperty("routeDetails")
    private RouteStatsResponse route;
    @JsonProperty("saved_at")
    private LocalDateTime savedAt;
}
