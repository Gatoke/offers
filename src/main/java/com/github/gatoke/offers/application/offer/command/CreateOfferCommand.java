package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.offer.vo.OfferType;
import com.github.gatoke.offers.domain.shared.Currency;
import com.github.gatoke.offers.domain.shared.Money;
import com.github.gatoke.offers.domain.user.vo.UserId;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateOfferCommand implements Command {

    private final OfferId offerId;
    private final UserId userId;
    private final String title;
    private final String content;
    private final OfferType offerType;
    private final Money price;

    public CreateOfferCommand(final long userId,
                              final String title,
                              final String content,
                              final String offerType,
                              final String currency,
                              final BigDecimal price) {
        this.offerId = OfferId.newId();
        this.userId = UserId.of(userId);
        this.title = title;
        this.content = content;
        this.offerType = OfferType.of(offerType);
        this.price = Money.from(Currency.from(currency), price);
    }
}
