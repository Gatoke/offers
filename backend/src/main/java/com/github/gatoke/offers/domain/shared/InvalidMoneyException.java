package com.github.gatoke.offers.domain.shared;

import java.math.BigDecimal;

import static java.lang.String.format;

public class InvalidMoneyException extends RuntimeException {

    public InvalidMoneyException(final Currency currency, final BigDecimal amount) {
        super(format("Cannot create Money from: currency:%s ; amount:%s", currency, amount));
    }
}
