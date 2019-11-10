package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OfferCreatedEvent extends Event {

    private final Offer offer;
}
