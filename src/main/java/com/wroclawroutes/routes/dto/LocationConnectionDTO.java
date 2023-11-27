package com.wroclawroutes.routes.dto;

import com.wroclawroutes.routes.entity.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class LocationConnectionDTO {
    private LocationDTO startLocation;
    private LocationDTO endLocation;
    private Integer timeInMiliSeconds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationConnectionDTO that = (LocationConnectionDTO) o;
        return Objects.equals(startLocation, that.startLocation) && Objects.equals(endLocation, that.endLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startLocation, endLocation);
    }
}
