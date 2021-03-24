package com.github.gatoke.offers.domain.shared;

import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public enum Currency {

    PLN("PLN"),
    USD("USD"),
    EUR("EUR");

    private final String code;

    public static Currency from(final String currency) {
        return stream(values())
                .filter(value -> value.code.equalsIgnoreCase(currency))
                .findFirst()
                .orElseThrow(() -> new InvalidCurrencyException(currency));
    }
}
