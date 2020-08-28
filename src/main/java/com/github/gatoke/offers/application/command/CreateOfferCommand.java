package com.github.gatoke.offers.application.command;

import lombok.Getter;

@Getter
public class CreateOfferCommand {

    private final long userId;
    private final String title;
    private final String content;

    public CreateOfferCommand(final long userId, final String title, final String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
