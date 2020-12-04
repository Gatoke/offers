package com.github.gatoke.offers.domain.shared;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public abstract class Aggregate {

    private List<DomainEvent<?>> domainEvents = new ArrayList<>();

    protected void registerEvent(final DomainEvent<?> domainEvent) {
        domainEvents.add(domainEvent);
    }

    public List<DomainEvent<?>> pickDomainEvents() {
        final List<DomainEvent<?>> eventsToReturn = unmodifiableList(domainEvents);
        domainEvents = new ArrayList<>();
        return eventsToReturn;
    }
}
