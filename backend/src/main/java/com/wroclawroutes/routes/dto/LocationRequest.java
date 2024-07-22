package com.wroclawroutes.routes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class LocationRequest {
    private String name;
    private String address;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationRequest that = (LocationRequest) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }
}
