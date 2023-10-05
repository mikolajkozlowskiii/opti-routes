package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.UserRouteSaved;
import com.wroclawroutes.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRouteSavedRepository extends JpaRepository<UserRouteSaved, Long> {
    Optional<UserRouteSaved> findById(Long id);
    List<UserRouteSaved> findAllByUserOrderByCreatedAt(User user);
}
