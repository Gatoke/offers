package com.github.gatoke.offers.domain.user.exception;

import com.github.gatoke.offers.domain.user.vo.UserId;

import static java.lang.String.format;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(final UserId userId) {
        super(format("User with id: %s already exists.", userId));
    }
}
