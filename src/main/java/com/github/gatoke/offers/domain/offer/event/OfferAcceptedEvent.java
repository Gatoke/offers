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

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_ACCEPTED;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class OfferAcceptedEvent implements DomainEvent<OfferAcceptedEvent.Payload> {

    private final EventType type = OFFER_ACCEPTED;

    private UUID id;
    private Time occurredOn;
    private Payload payload;

    public OfferAcceptedEvent(final UUID offerId, final OfferStatus status) {
        this.id = UUID.randomUUID();
        this.occurredOn = Time.now();
        this.payload = new Payload(offerId, status);
    }

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {
        private UUID offerId;
        private OfferStatus status;
    }
}
