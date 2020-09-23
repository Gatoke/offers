package com.github.gatoke.offers.application.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AcceptOfferCommand {

    private final UUID offerId;

    public AcceptOfferCommand(final String offerId) {
        this.offerId = UUID.fromString(offerId);
    }
}
