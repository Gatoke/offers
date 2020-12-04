package com.github.gatoke.offers.domain.shared;

import java.util.UUID;

public interface DomainEvent<P> {

    UUID getId();

    EventType getType();

    Time getOccurredOn();

    P getPayload();

}
