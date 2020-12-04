package com.github.gatoke.offers.domain.shared;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.event.*;
import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {

    USER_REGISTERED(UserRegisteredEvent.class, User.class),
    OFFER_CREATED(OfferCreatedEvent.class, Offer.class),
    OFFER_ACCEPTED(OfferAcceptedEvent.class, OfferAcceptedEvent.Payload.class),
    OFFER_DELETED(OfferDeletedEvent.class, OfferDeletedEvent.Payload.class),
    OFFER_EXPIRED(OfferExpiredEvent.class, OfferExpiredEvent.Payload.class),
    OFFER_FINISHED(OfferFinishedEvent.class, OfferFinishedEvent.Payload.class),
    OFFER_PUBLISHED(OfferPublishedEvent.class, OfferPublishedEvent.Payload.class),
    OFFER_REJECTED(OfferRejectedEvent.class, OfferRejectedEvent.Payload.class);

    private final Class<? extends DomainEvent> target;
    private final Class<?> targetPayload;
}
