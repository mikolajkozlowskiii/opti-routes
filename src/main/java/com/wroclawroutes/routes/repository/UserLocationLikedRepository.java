package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.UserLocationLiked;
import com.wroclawroutes.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLocationLikedRepository extends JpaRepository<UserLocationLiked, Long> {
    Optional<UserLocationLiked> findById(Long id);
    List<UserLocationLiked> findAllByUserOrderByCreatedAtAsc(User user);
    List<UserLocationLiked> findAllByUserOrderByCreatedAtDesc(User user);
    @Query("SELECT SUM(CASE WHEN ul.isLiked = TRUE THEN 1 ELSE -1 END) \n" +
            "FROM UserLocationLiked ul " +
            "WHERE ul.location = :location")
    Long countLikedLocation(@Param("location") Location location);

}