package com.github.gatoke.offers.domain.offer.vo;

import com.github.gatoke.offers.domain.offer.exception.UnknownOfferTypeException;

import static java.util.Arrays.stream;

public enum OfferType {

    BUY, SELL;

    public static OfferType from(final String type) {
        return stream(values())
                .filter(offerType -> offerType.toString().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new UnknownOfferTypeException(type));
    }
}
