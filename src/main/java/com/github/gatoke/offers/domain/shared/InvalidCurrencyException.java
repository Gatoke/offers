package com.github.gatoke.offers.domain.shared;

import static java.lang.String.format;

public class InvalidCurrencyException extends RuntimeException {

    public InvalidCurrencyException(final String currency) {
        super(format("Currency: %s is incorrect.", currency));
    }
}
