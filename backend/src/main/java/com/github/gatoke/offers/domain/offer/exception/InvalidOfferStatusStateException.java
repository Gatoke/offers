package com.github.gatoke.offers.domain.offer.exception;

import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import lombok.Getter;

import static java.lang.String.format;

@Getter
public class InvalidOfferStatusStateException extends RuntimeException {

    public InvalidOfferStatusStateException(final OfferId id, final OfferStatus status) {
        super(format("Invalid offer status state. Offer id: %s, Status: %s", id.toString(), status.toString()));
    }
}
