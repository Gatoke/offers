package com.github.gatoke.offers.application.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RejectOfferCommand {

    private final UUID offerId;
    private final String reason;

    public RejectOfferCommand(final String offerId, final String reason) {
        this.offerId = UUID.fromString(offerId);
        this.reason = reason;
    }
}
