package com.example.wroclawroutes.repositories;

import com.example.wroclawroutes.entities.ERole;
import com.example.wroclawroutes.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}