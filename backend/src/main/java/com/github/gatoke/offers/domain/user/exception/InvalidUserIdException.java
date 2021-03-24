package com.github.gatoke.offers.domain.user.exception;

public class InvalidUserIdException extends RuntimeException {

    public InvalidUserIdException(final String message) {
        super(message);
    }
}
