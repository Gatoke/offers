package com.github.gatoke.offers.domain.shared;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class Money {

    private Currency currency;
    private BigDecimal amount;

    public static Money from(final Currency currency, final BigDecimal amount) {
        if (currency == null || amount == null) {
            throw new InvalidMoneyException(currency, amount);
        }
        return new Money(currency, amount);
    }
}
