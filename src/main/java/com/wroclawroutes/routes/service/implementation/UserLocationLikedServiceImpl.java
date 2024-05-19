package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.app.dto.ApiResponse;
import com.wroclawroutes.routes.dto.LocationLikeRequest;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.UserLocationLiked;
import com.wroclawroutes.routes.repository.UserLocationLikedRepository;
import com.wroclawroutes.routes.service.LocationService;
import com.wroclawroutes.routes.service.UserLocationLikedService;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationLikedServiceImpl implements UserLocationLikedService {
    private final UserLocationLikedRepository userLocationLikedRepository;
    private final UserService userService;
    private final LocationService locationService;

    @Override
    public ApiResponse save(long locationId, UserDetailsImpl currentUser, LocationLikeRequest likeRequest) {
        final User user = userService.getCurrentUser(currentUser);
        final Location location = locationService.findById(locationId);
        UserLocationLiked userLocationLiked = userLocationLikedRepository
                .findByUserAndLocation(user, location)
                .orElse(UserLocationLiked.builder().user(user).location(location).build());

        userLocationLiked.setIsLiked(likeRequest.getIsLiked());
        userLocationLiked.setCreatedAt(Instant.now());

        userLocationLikedRepository.save(userLocationLiked);

        if(likeRequest.getIsLiked()){
            return new ApiResponse(Boolean.TRUE, "Location id " + location.getId() + " has been liked by " + user.getEmail());
        }
        return new ApiResponse(Boolean.TRUE, "Location id " + location.getId() + " has been disliked by " + user.getEmail());
    }

    @Override
    public UserLocationLiked findById(Long id) {
        return null;
    }

    @Override
    public List<UserLocationLiked> findAllByUserOrderByCreatedAtAsc(User user) {
        return null;
    }

    @Override
    public List<UserLocationLiked> findAllByLocation(Location location) {
        return null;
    }

    @Override
    public List<UserLocationLiked> findAllByUserOrderByCreatedAtDesc(User user) {
        return null;
    }

    @Override
    public Long countLikedLocation(Location location) {
        return null;
    }
}
