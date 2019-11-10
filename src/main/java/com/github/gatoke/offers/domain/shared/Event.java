package com.github.gatoke.offers.domain.shared;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public abstract class Event {

    protected final UUID id = UUID.randomUUID();
    protected final String type = this.getClass().getSimpleName();
    protected final Time occurredOn = Time.now();
}
