package com.wroclawroutes.routes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Entity
@Table(
        name = "routes_steps",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_route_step",
                        columnNames = {"route_id", "step"}
                )
        }
        )
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RouteStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @NotNull
    private Integer step;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteStep routeStep = (RouteStep) o;
        return Objects.equals(id, routeStep.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
