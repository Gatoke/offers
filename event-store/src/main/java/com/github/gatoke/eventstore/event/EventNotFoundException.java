package com.github.gatoke.eventstore.event;

import java.util.UUID;

import static java.lang.String.format;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(final UUID eventId) {
        super(format("Event of id: %s not found.", eventId.toString()));
    }
}
