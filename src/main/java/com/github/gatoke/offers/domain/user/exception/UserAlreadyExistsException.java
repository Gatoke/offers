package com.github.gatoke.offers.domain.user.exception;

import static java.lang.String.format;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(final long userId) {
        super(format("User with id: %s already exists.", userId));
    }
}
