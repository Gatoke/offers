package com.github.gatoke.offers.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class DeleteOfferCommand {

    private final UUID offerId;

    public static DeleteOfferCommand of(final String offerId) {
        return new DeleteOfferCommand(
                UUID.fromString(offerId)
        );
    }
}
