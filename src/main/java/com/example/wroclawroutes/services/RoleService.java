package com.example.wroclawroutes.services;

import com.example.wroclawroutes.entities.ERole;
import com.example.wroclawroutes.entities.Role;
import com.example.wroclawroutes.entities.User;

import java.util.Set;

public interface RoleService {
    Set<Role> getDefaultRolesForUser();
    Role getRole(ERole role);
    boolean checkIfUserHasRole(User user, Role role);
}
