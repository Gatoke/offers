package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import lombok.Getter;

@Getter
public class RejectOfferCommand implements Command {

    private final OfferId offerId;
    private final String reason;

    public RejectOfferCommand(final String offerId, final String reason) {
        this.offerId = OfferId.of(offerId);
        this.reason = reason;
    }
}
