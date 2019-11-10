package com.github.gatoke.offers.domain.offer.exception;

import static java.lang.String.format;

public class UnknownOfferStatusException extends RuntimeException {

    public UnknownOfferStatusException(final String unknownStatus) {
        super(format("Unknown offer status: %s", unknownStatus));
    }
}
