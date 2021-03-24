package com.github.gatoke.eventstore.handler;

import com.github.gatoke.eventstore.DomainEventHandler;

import java.util.Set;

/**
 * Collection of declared event handlers available in a runtime.
 *
 * @see DomainEventHandler
 */
public interface EventHandlerRegistry {

    /**
     * @param eventType - type of an event to retrieve handlers for.
     * @return collection of event handlers loaded in a runtime.
     */
    Set<EventHandler> findAllBy(final String eventType);

}
