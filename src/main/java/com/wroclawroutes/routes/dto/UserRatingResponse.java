package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingResponse {
    @JsonProperty("user_email")
    private String userEmail;
    private Integer rate;
    @JsonProperty("rated_at")
    private Instant ratedAt;
}
