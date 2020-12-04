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

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_PUBLISHED;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class OfferPublishedEvent implements DomainEvent<OfferPublishedEvent.Payload> {

    private final EventType type = OFFER_PUBLISHED;

    private UUID id;
    private Time occurredOn;
    private Payload payload;

    public OfferPublishedEvent(final UUID offerId, final OfferStatus offerStatus) {
        this.id = UUID.randomUUID();
        this.occurredOn = Time.now();
        this.payload = new Payload(offerId, offerStatus);
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
