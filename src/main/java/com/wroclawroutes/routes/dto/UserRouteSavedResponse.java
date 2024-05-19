package com.wroclawroutes.routes.dto;

import com.wroclawroutes.users.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class UserRouteSavedResponse {
    private UserResponse user;
    private RouteStatsResponse route;
    private LocalDateTime createdAt;
}
