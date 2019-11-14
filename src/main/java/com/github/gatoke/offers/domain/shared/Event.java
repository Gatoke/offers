package com.github.gatoke.offers.domain.shared;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Event<P> {

    private final UUID eventId = UUID.randomUUID();
    private final String eventType = this.getClass().getSimpleName();
    private final Time occurredOn = Time.now();

    public abstract P getPayload();
}
