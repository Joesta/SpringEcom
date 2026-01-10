package com.telusko.SpringEcom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * @author Joesta
 */

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredentialsRequiredException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CredentialsRequiredException(String message) {
        super(message);
    }
}
