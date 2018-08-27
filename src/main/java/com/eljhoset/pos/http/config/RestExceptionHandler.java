package com.eljhoset.pos.http.config;

import com.eljhoset.pos.http.model.ApiError;
import com.eljhoset.pos.user.exception.UserNotActiveException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Daniel
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ApiError handleException(UserNotActiveException ex) {
        return new ApiError(ex.getMessage(), ApiError.ApiErrorType.AUTHORIZATION, 40102);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ApiError handleException(BadCredentialsException ex) {
        return new ApiError(ex.getMessage(), ApiError.ApiErrorType.AUTHORIZATION, 40101);
    }
}
