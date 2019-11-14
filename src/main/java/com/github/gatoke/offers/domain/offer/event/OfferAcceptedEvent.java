package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
public class OfferAcceptedEvent extends Event<OfferAcceptedEvent.Payload> {

    private final Payload payload;

    public OfferAcceptedEvent(UUID offerId, OfferStatus status) {
        this.payload = new Payload(offerId, status);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Payload {
        private final UUID offerId;
        private final OfferStatus status;
    }
}
