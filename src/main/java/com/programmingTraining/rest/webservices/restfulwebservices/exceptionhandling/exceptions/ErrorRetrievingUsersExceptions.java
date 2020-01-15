package com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorRetrievingUsersExceptions extends RuntimeException {
    public ErrorRetrievingUsersExceptions(String message) {
        super(message);
    }
}
