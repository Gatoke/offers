package com.github.gatoke.offers.domain.offer.vo;

import com.github.gatoke.offers.domain.offer.exception.UnknownOfferTypeException;

import java.util.Arrays;
import java.util.Objects;

public enum OfferType {

    BUY, SELL;

    public static OfferType of(final String type) {
        return Arrays.stream(values())
                .filter(Objects::nonNull)
                .filter(offerType -> offerType.toString().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new UnknownOfferTypeException(type));
    }
}
