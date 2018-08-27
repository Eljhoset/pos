package com.eljhoset.pos.http.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Daniel
 */
@Getter
@AllArgsConstructor
public class ApiError {

    private final String message;
    private final ApiErrorType type;
    private final int code;

    public static enum ApiErrorType {
        AUTHORIZATION
    }
}
