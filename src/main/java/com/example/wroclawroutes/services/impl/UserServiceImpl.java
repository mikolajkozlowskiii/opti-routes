package com.example.wroclawroutes.services.impl;

import com.example.wroclawroutes.dto.SignUpRequest;
import com.example.wroclawroutes.dto.UserRequest;
import com.example.wroclawroutes.entities.ERole;
import com.example.wroclawroutes.entities.Role;
import com.example.wroclawroutes.entities.User;
import com.example.wroclawroutes.exceptions.ApiException;
import com.example.wroclawroutes.exceptions.UnauthorizedException;
import com.example.wroclawroutes.exceptions.UserNotFoundException;
import com.example.wroclawroutes.repositories.UserRepository;
import com.example.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.example.wroclawroutes.services.RoleService;
import com.example.wroclawroutes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

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
    public User updateUser(UserRequest request, String email, UserDetailsImpl currentUser) {
        User userToUpdate = findUserByEmail(email);
        if(checkIfUserHasPermissionToModify(userToUpdate, currentUser)){
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

    private boolean checkIfUserHasPermissionToModify(User userToModify, UserDetailsImpl currentUser){
        return userToModify.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(roleService.getRole(ERole.ROLE_ADMIN));
    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return !userRepository.existsByEmail(email);    }

    @Override
    public int enableUser(String email) {
        return userRepository.enableAppUser(email);
    }

    @Override
    public void deleteUser(String email, UserDetailsImpl currentUser) {
        final User user = findUserByEmail(email);
        if(checkIfUserHasPermissionToModify(user, currentUser)){
            userRepository.delete(user);
        }
        else {
            throw new UnauthorizedException("delete not your account.");
        }
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
}
