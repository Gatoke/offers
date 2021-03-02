package com.github.gatoke.offers.infrastructure.eventbus.handler;

import java.util.Set;

public interface EventHandlerRegistry {

    Set<EventHandler> findAllFor(final Class<?> eventType);

}
