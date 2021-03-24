package com.github.gatoke.offers.domain.offer.exception;

import static java.lang.String.format;

public class UnknownOfferTypeException extends RuntimeException {

    public UnknownOfferTypeException(final String offerType) {
        super(format("Unknown offer type: %s", offerType));
    }
}
