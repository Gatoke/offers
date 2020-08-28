package com.github.gatoke.offers.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class AcceptOfferCommand {

    private final UUID offerId;

    public static AcceptOfferCommand of(final String offerId) {
        return new AcceptOfferCommand(UUID.fromString(offerId));
    }
}
