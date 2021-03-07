package com.github.gatoke.offers.eventstore.event;

import java.util.UUID;

import static java.lang.String.format;

class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(final UUID eventId) {
        super(format("Event of id: %s not found.", eventId.toString()));
    }
}
