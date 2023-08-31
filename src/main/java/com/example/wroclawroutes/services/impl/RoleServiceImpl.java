package com.example.wroclawroutes.services.impl;

import com.example.wroclawroutes.entities.ERole;
import com.example.wroclawroutes.entities.Role;
import com.example.wroclawroutes.entities.User;
import com.example.wroclawroutes.exceptions.RoleNotFoundException;
import com.example.wroclawroutes.repositories.RoleRepository;
import com.example.wroclawroutes.repositories.UserRepository;
import com.example.wroclawroutes.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Override
    public Set<Role> getDefaultRolesForUser() {
        if (userRepository.count() > 0) {
            return Set.of(getRole(ERole.ROLE_USER));
        }
        return Set.of(getRole(ERole.ROLE_USER), getRole(ERole.ROLE_ADMIN));
    }

    @Override
    public Role getRole(ERole role) {
        return roleRepository
                .findByName(role)
                .orElseThrow(() -> new RoleNotFoundException("Put in database role: " + role));
    }

    @Override
    public boolean checkIfUserHasRole(User user, Role role) {
        return user.getRoles().contains(role);
    }
}
