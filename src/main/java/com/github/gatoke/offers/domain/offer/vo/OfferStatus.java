package com.github.gatoke.offers.domain.offer.vo;

import com.github.gatoke.offers.domain.offer.exception.UnknownOfferStatusException;

import java.util.Arrays;

public enum OfferStatus {

    PENDING,
    ACCEPTED,
    REJECTED,
    PUBLISHED,
    FINISHED,
    EXPIRED,
    DELETED;

    public static OfferStatus of(final String status) {
        return Arrays.stream(values())
                     .filter(offerStatus -> status != null && offerStatus.toString()
                                                                         .equalsIgnoreCase(status.replace(" ", "")))
                     .findFirst()
                     .orElseThrow(() -> new UnknownOfferStatusException(status));
    }
}
