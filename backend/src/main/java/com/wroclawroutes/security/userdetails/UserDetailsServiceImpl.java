package com.wroclawroutes.security.userdetails;

import com.wroclawroutes.users.entities.User;
import com.wroclawroutes.email.exceptions.NotConfirmedEmailException;
import com.wroclawroutes.users.exceptions.UserNotFoundException;
import com.wroclawroutes.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        final User user = findUserByEmail(email);
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserByUserId(Long id) throws UserNotFoundException {
        final User user = findUserById(id);
        return UserDetailsImpl.build(user);
    }

    private User findUserByEmail(String email) {
        final User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        checkIsUserEnabled(user);
        return user;
    }

    private static void checkIsUserEnabled(User user) {
        if(!user.isEnabled()){
            throw new NotConfirmedEmailException(user.getEmail());
        }
    }


    private User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }
}
