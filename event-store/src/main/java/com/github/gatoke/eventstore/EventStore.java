package com.github.gatoke.eventstore;

import javax.validation.constraints.NotNull;


/**
 * @author Karol Dominiak <gatoke2@gmail.com>
 * <p>
 * Provides methods for registering and processing events.
 */
public interface EventStore {

    /**
     * Stores an event and creates a new {@link com.github.gatoke.eventstore.process.EventHandlerProcess}
     * Requires an opened JPA transaction.
     *
     * @param event     - payload of a business event
     * @param eventType - type of the event
     * @see com.github.gatoke.eventstore.event.StoredEvent
     * @see com.github.gatoke.eventstore.process.EventHandlerProcess
     */
    void append(@NotNull final Object event, final String eventType);

    /**
     * Executes pending event handler processes.
     * Changes status of {@link com.github.gatoke.eventstore.process.EventHandlerProcess} depending on a result.
     *
     * @see EventHandlerExecutor
     * @see com.github.gatoke.eventstore.process.EventHandlerProcess
     * @see com.github.gatoke.eventstore.process.EventHandlerProcessStatus
     */
    void processPendingEvents();

    /**
     * Destroys processes with status success {@link com.github.gatoke.eventstore.process.EventHandlerProcessStatus#SUCCESS}.
     *
     * @see com.github.gatoke.eventstore.process.EventHandlerProcessStatus
     */
    void cleanSucceedProcesses();
}
