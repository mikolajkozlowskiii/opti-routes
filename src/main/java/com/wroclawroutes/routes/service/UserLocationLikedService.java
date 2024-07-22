package com.wroclawroutes.routes.service;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.LocationLikeRequest;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.UserLocationLiked;
import com.wroclawroutes.security.userdetails.CurrentUser;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.entities.User;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UserLocationLikedService {
    ApiResponse save(long locationId, UserDetailsImpl currentUser, LocationLikeRequest likeRequest);
    UserLocationLiked findById(Long id);
    List<UserLocationLiked> findAllByUserOrderByCreatedAtAsc(User user);
    List<UserLocationLiked> findAllByLocation(Location location);

    List<UserLocationLiked> findAllByUserOrderByCreatedAtDesc(User user);
    Long countLikedLocation(Location location);
}
