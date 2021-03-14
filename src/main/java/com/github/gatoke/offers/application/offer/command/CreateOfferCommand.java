package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.user.vo.UserId;
import lombok.Getter;

@Getter
public class CreateOfferCommand implements Command {

    private final OfferId offerId;
    private final UserId userId;
    private final String title;
    private final String content;

    public CreateOfferCommand(final long userId, final String title, final String content) {
        this.offerId = OfferId.newId();
        this.userId = UserId.of(userId);
        this.title = title;
        this.content = content;
    }
}