package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OfferCreatedEvent extends Event<OfferCreatedEvent.Payload> {

    private final Payload payload;

    public OfferCreatedEvent(final Offer offer) {
        this.payload = new Payload(offer);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Payload {
        private final Offer offer;
    }
}
