package com.github.gatoke.offers.domain.user.exception;

import static java.lang.String.format;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(final String email) {
        super(format("Email: %s is incorrect.", email));
    }
}
