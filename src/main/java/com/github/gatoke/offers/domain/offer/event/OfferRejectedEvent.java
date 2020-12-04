package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import com.github.gatoke.offers.domain.shared.Time;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_REJECTED;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class OfferRejectedEvent implements DomainEvent<OfferRejectedEvent.Payload> {

    private final EventType type = OFFER_REJECTED;

    private UUID id;
    private Time occurredOn;
    private Payload payload;

    public OfferRejectedEvent(final UUID offerId, final OfferStatus offerStatus, final String reason) {
        this.id = UUID.randomUUID();
        this.occurredOn = Time.now();
        this.payload = new Payload(offerId, offerStatus, reason);
    }

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {
        private UUID offerId;
        private OfferStatus status;
        private String reason;
    }
}
