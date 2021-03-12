package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteOfferCommand implements Command {

    private final UUID offerId;

    public DeleteOfferCommand(final String offerId) {
        this.offerId = UUID.fromString(offerId);
    }
}
