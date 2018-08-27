package com.eljhoset.pos.user.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Daniel
 */
public class UserNotActiveException extends AuthenticationException {

    public UserNotActiveException(String message) {
        super(message);
    }

}
