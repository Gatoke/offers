package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import com.github.gatoke.offers.domain.shared.Time;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_CREATED;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class OfferCreatedEvent implements DomainEvent<Offer> {

    private final EventType type = OFFER_CREATED;

    private UUID id;
    private Time occurredOn;
    private Offer payload;

    public OfferCreatedEvent(final Offer offer) {
        this.id = UUID.randomUUID();
        this.occurredOn = Time.now();
        this.payload = offer;
    }
}
