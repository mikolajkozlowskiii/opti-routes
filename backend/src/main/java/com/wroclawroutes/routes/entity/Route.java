package com.wroclawroutes.routes.entity;

import com.wroclawroutes.users.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Route {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Size(min = 5, max = 250)
    private String description;
    @Column(name = "created_at")  @CreationTimestamp
    private LocalDateTime createdAt;
    @NotNull
    private Integer distanceInMeters;
    @NotNull
    private Integer timeInMilliseconds;
    private Boolean isPublic;
    @ManyToMany
    @JoinTable(
            name = "routes_tags",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    @OneToMany(mappedBy = "route", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private Set<UserRouteSaved> usersSavedRoutes;
    @OneToMany(mappedBy = "route", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private Set<UserRouteRating> ratings;
    @OneToMany(mappedBy = "route", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private Set<RouteStep> steps;

    public void addRouteStep(RouteStep routeStep){
        this.steps.add(routeStep);
        routeStep.setRoute(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}






