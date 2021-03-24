package com.github.gatoke.offers.application.offer.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpireOfferCommand implements Command {

    private final OfferId offerId;
}
