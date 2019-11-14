package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
public class OfferExpiredEvent extends Event<OfferExpiredEvent.Payload> {

    private final Payload payload;

    public OfferExpiredEvent(final UUID offerId, final OfferStatus offerStatus) {
        this.payload = new Payload(offerId, offerStatus);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Payload {
        private final UUID offerId;
        private final OfferStatus status;
    }
}
