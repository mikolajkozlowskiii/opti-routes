package com.wroclawroutes.routes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(
        name = "locations",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "UQ_location",
                columnNames = {"name", "latitude", "longitude"}
        )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 50)
    private String name;
    @Size(min = 3, max = 50)
    private String address;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @OneToMany(mappedBy = "startLocation", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<LocationConnection> outgoingConnections;
    // TODO not sure about incomingConnections, check what happend when removed
    @OneToMany(mappedBy = "location", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<UserLocationLiked> likedByUsers;
}
