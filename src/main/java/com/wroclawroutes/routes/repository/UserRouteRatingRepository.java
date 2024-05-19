package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.UserRouteRating;
import com.wroclawroutes.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRouteRatingRepository extends JpaRepository<UserRouteRating, Long> {
    Optional<UserRouteRating> findById(Long id);
    Boolean existsByRouteAndUser(Route route, User user);
    Optional<UserRouteRating> findByRouteAndUser(Route route, User user);
    List<UserRouteRating> findAllByUserOrderByCreatedAtDesc(User user);
    List<UserRouteRating> findAllByRoute(Route route);
}
