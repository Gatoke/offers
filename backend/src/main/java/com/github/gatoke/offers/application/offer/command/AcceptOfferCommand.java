package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import lombok.Getter;

@Getter
public class AcceptOfferCommand implements Command {

    private final OfferId offerId;

    public AcceptOfferCommand(final String offerId) {
        this.offerId = OfferId.from(offerId);
    }
}
