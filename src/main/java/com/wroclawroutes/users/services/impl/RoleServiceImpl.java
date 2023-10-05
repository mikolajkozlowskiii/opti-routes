package com.wroclawroutes.users.services.impl;

import com.wroclawroutes.users.entities.ERole;
import com.wroclawroutes.users.entities.Role;
import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.users.exceptions.RoleNotFoundException;
import com.wroclawroutes.users.repositories.RoleRepository;
import com.wroclawroutes.users.repositories.UserRepository;
import com.wroclawroutes.users.services.RoleService;
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
