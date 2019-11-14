package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class OfferRejectedEvent extends Event<OfferRejectedEvent.Payload> {

    private final Payload payload;

    public OfferRejectedEvent(final UUID offerId, final OfferStatus offerStatus, final String reason) {
        this.payload = new Payload(offerId, offerStatus, reason);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Payload {
        private final UUID offerId;
        private final OfferStatus status;
        private final String reason;
    }
}
