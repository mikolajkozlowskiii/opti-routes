package com.wroclawroutes.users.services;

import com.wroclawroutes.users.entities.ERole;
import com.wroclawroutes.users.entities.Role;
import com.wroclawroutes.users.entities.User;

import java.util.Set;

public interface RoleService {
    Set<Role> getDefaultRolesForUser();
    Role getRole(ERole role);
    boolean checkIfUserHasRole(User user, Role role);
}
