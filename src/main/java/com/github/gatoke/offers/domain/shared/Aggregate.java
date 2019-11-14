package com.github.gatoke.offers.domain.shared;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public abstract class Aggregate {

    private List<Event<?>> domainEvents = new ArrayList<>();

    protected void registerEvent(final Event<?> domainEvent) {
        domainEvents.add(domainEvent);
    }

    public List<Event<?>> pickDomainEvents() {
        final List<Event<?>> eventsToReturn = unmodifiableList(domainEvents);
        domainEvents = new ArrayList<>();
        return eventsToReturn;
    }
}
