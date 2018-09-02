package com.eljhoset.pos.user.service;

import com.eljhoset.pos.user.exception.UserNotActiveException;
import com.eljhoset.pos.user.model.jpa.Users;
import com.eljhoset.pos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Users login(String username, String password) {
        Users users = findUserByUsername(username);
        if (!users.isActive()) {
            throw new UserNotActiveException(String.format("User[%s] is no active", username));
        }
        if (passwordEncoder.matches(password, users.getPassword())) {
            return users;
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    public Users findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException(String.format("User[%s] Not Found", username));
        });
    }

}
