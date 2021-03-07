package com.github.gatoke.offers.eventstore.handler;

import com.github.gatoke.offers.eventstore.DomainEventHandler;

import java.util.Set;

/**
 * Collection of declared event handlers in the runtime.
 *
 * @see DomainEventHandler
 */
public interface EventHandlerRegistry {

    /**
     * @param eventType - type of an event to load handlers for.
     * @return collection of event handlers loaded in the runtime.
     */
    Set<EventHandler> findAllBy(final String eventType);

}
