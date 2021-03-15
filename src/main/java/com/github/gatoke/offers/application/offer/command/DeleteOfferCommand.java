package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import lombok.Getter;

@Getter
public class DeleteOfferCommand implements Command {

    private final OfferId offerId;

    public DeleteOfferCommand(final String offerId) {
        this.offerId = OfferId.of(offerId);
    }
}
