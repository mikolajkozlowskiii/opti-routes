package com.wroclawroutes.routes.entity;

import com.wroclawroutes.users.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(
        name = "users_routes_ratings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_user_ra_rate",
                        columnNames = {"user_id", "route_id"}
                )
        }
        )
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRouteRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;
    @CreationTimestamp
    private Instant createdAt;
    @Min(value = 1)
    @Max(value = 10)
    private Integer rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRouteRating that = (UserRouteRating) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
