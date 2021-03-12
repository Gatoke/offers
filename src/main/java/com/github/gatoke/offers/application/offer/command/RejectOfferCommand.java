package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RejectOfferCommand implements Command {

    private final UUID offerId;
    private final String reason;

    public RejectOfferCommand(final String offerId, final String reason) {
        this.offerId = UUID.fromString(offerId);
        this.reason = reason;
    }
}
