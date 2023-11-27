package com.wroclawroutes.routes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
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
    private Set<LocationConnection> outgoingConnections = new HashSet<>();
    // TODO not sure about incomingConnections, check what happend when removed
    @OneToMany(mappedBy = "location", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<UserLocationLiked> likedByUsers = new HashSet<>();

    public void addOutgoingConnection(LocationConnection connection){
        outgoingConnections.add(connection);
        connection.setStartLocation(this);
    }

    public void removeOutgoingConnection(LocationConnection connection){
        outgoingConnections.remove(connection);
        connection.setStartLocation(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
