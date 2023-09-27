package com.wroclawroutes.users.entities;

import com.wroclawroutes.routes.entity.UserLocationLiked;
import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.routes.entity.UserRouteSaved;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAdditionalDetails {
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private Set<UserLocationLiked> likedLocations;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private Set<UserRouteSaved> savedRoutes;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<UserRouteRating> ratedRoutes;
}
