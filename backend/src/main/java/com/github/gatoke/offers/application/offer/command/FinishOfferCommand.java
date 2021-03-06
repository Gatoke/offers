package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FinishOfferCommand implements Command {

    private final OfferId offerId;

    public FinishOfferCommand(final UUID offerId) {
        this.offerId = OfferId.from(offerId);
    }
}
