package com.github.gatoke.offers.domain.user.exception;

import com.github.gatoke.offers.domain.user.vo.UserId;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final long id) {
        super(format("User with id: %s not found.", id));
    }

    public UserNotFoundException(final UserId id) {
        super(format("User with id: %s not found.", id.getValue()));
    }
}
