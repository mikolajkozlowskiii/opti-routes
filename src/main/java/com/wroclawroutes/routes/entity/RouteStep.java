package com.wroclawroutes.routes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
}
