package com.github.gatoke.offers.domain.shared;

import com.github.gatoke.offers.domain.offer.event.*;
import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {

    USER_REGISTERED(UserRegisteredEvent.class),
    OFFER_CREATED(OfferCreatedEvent.class),
    OFFER_ACCEPTED(OfferAcceptedEvent.class),
    OFFER_DELETED(OfferDeletedEvent.class),
    OFFER_EXPIRED(OfferExpiredEvent.class),
    OFFER_FINISHED(OfferFinishedEvent.class),
    OFFER_PUBLISHED(OfferPublishedEvent.class),
    OFFER_REJECTED(OfferRejectedEvent.class);

    private final Class<? extends DomainEvent> target;
}
