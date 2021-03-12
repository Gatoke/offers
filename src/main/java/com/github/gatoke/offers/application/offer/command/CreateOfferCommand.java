package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateOfferCommand implements Command {

    private final UUID offerId;
    private final long userId;
    private final String title;
    private final String content;

    public CreateOfferCommand(final long userId, final String title, final String content) {
        this.offerId = UUID.randomUUID();
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
