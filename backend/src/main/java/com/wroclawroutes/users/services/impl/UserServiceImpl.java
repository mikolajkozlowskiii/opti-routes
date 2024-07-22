package com.wroclawroutes.users.services.impl;

import com.wroclawroutes.security.dto.SignUpRequest;
import com.wroclawroutes.users.dto.UserRequest;
import com.wroclawroutes.users.entities.ERole;
import com.wroclawroutes.users.entities.Role;
import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.app.exceptions.ApiException;
import com.wroclawroutes.security.exceptions.UnauthorizedException;
import com.wroclawroutes.users.exceptions.UserNotFoundException;
import com.wroclawroutes.users.repositories.UserRepository;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.users.services.RoleService;
import com.wroclawroutes.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Override
    public User updateUser(UserRequest request, String email, UserDetailsImpl currentUser) {
        User userToUpdate = findUserByEmail(email);
        if(isUserHasPermissionToModify(userToUpdate, currentUser)){
            final String firstName = request.getFirstName();
            final String lastName = request.getLastName();
            final String password = request.getPassword();
            if(firstName != null && !firstName.isEmpty()){
                userToUpdate.setFirstName(firstName);
            }
            if(lastName != null && !lastName.isEmpty()){
                userToUpdate.setLastName(lastName);
            }
            if(password != null && !password.isEmpty()){
                userToUpdate.setPassword(encoder.encode(password));
            }
            return userRepository.save(userToUpdate);
        }
        throw new UnauthorizedException("update not your account.");
    }

    @Override
    public void deleteUser(String email, UserDetailsImpl currentUser) {
        final User user = findUserByEmail(email);
        if(isUserHasPermissionToModify(user, currentUser)){
            userRepository.delete(user);
        }
        else {
            throw new UnauthorizedException("delete not your account.");
        }
    }

    private boolean isUserHasPermissionToModify(User userToModify, UserDetailsImpl currentUser){
        return userToModify.getId().equals(currentUser.getId())
                || getCurrentUser(currentUser).getRoles().contains(roleService.getRole(ERole.ROLE_ADMIN));
    }

    @Override
    public void addModeratorRole(String email) {
        final User user = findUserByEmail(email);
        final Role roleModerator = roleService.getRole(ERole.ROLE_MODERATOR);
        if(roleService.checkIfUserHasRole(user, roleModerator)){
            throw new ApiException(email + " has already role " + ERole.ROLE_MODERATOR.name());
        }
        user.getRoles().add(roleModerator);
        userRepository.save(user);
    }

    @Override
    public void removeModeratorRole(String email) {
        final User user = findUserByEmail(email);
        final Role roleModerator = roleService.getRole(ERole.ROLE_MODERATOR);
        if(!roleService.checkIfUserHasRole(user, roleModerator)){
            throw new ApiException(email + " hasn't got role " + ERole.ROLE_MODERATOR.name());
        }
        user.getRoles().remove(roleModerator);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllUsersWithOnlyUserRole() {
        return userRepository
                .findAll()
                .stream()
                .filter(
                        s-> !s.getRoles().contains(new Role(ERole.ROLE_MODERATOR))
                                && !s.getRoles().contains(new Role(ERole.ROLE_ADMIN)))
                .toList();
    }

    @Override
    public List<User> findAllUsersWithOnlyModeratorRole() {
        return userRepository
                .findAll()
                .stream()
                .filter(
                        s-> s.getRoles().contains(new Role(ERole.ROLE_MODERATOR))
                                && !s.getRoles().contains(new Role(ERole.ROLE_ADMIN)))
                .toList();
    }

    @Override
    public User createLocalUser(SignUpRequest request) {
        final User user =  User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .roles(roleService.getDefaultRolesForUser())
                .password(encoder.encode(request.getPassword()))
                .isEnabled(false)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(UserDetailsImpl userDetails) {
        if(Objects.isNull(userDetails)){
            throw new IllegalArgumentException("UserDetails instance can't be null");
        }
        return findUserById(userDetails.getId());
    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public int enableUser(String email) {
        return userRepository.enableAppUser(email);
    }




}
