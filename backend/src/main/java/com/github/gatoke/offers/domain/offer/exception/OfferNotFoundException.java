package com.github.gatoke.offers.domain.offer.exception;

import com.github.gatoke.offers.domain.offer.vo.OfferId;

import java.util.UUID;

import static java.lang.String.format;

public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(final OfferId offerId) {
        super(format("Offer with id: %s not found.", offerId.toString()));
    }

    public OfferNotFoundException(final UUID offerId) {
        super(format("Offer with id: %s not found.", offerId.toString()));
    }
}
