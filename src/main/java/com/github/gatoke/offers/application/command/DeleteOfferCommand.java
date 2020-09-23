package com.github.gatoke.offers.application.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteOfferCommand {

    private final UUID offerId;

    public DeleteOfferCommand(final String offerId) {
        this.offerId = UUID.fromString(offerId);
    }
}
