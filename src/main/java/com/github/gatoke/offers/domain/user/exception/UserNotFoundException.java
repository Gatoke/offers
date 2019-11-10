package com.github.gatoke.offers.domain.user.exception;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String id) {
        super(format("User with id: %s not found.", id));
    }

    public UserNotFoundException(final Long id) {
        super(format("User with id: %s not found.", id.toString()));
    }
}
