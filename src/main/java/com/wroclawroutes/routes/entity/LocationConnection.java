package com.wroclawroutes.routes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(	name = "locations_connection",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_start_end_locations",
                        columnNames = {"start_location_id", "end_location_id"}
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LocationConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_location_id")
    private Location startLocation;
    @NotNull
    @JoinColumn(name = "end_location_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Location endLocation;
    private Integer distanceInMeters;
    private Integer timeOnFootInSec;
    private Integer timeByBusInSec;
    private Integer timeByCarInSec;

    @PrePersist
    @PreUpdate
    private void validateStartEndLocations() {
        if (startLocation != null && startLocation.equals(endLocation)) {
            throw new IllegalArgumentException("startLocation cannot be the same as endLocation");
        }
    }
}






