package com.wroclawroutes.routes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {
    @Min(0)
    @Max(10)
    private Integer rating;
}
