package com.github.gatoke.eventstore.handler;

import com.github.gatoke.eventstore.DomainEventHandler;

/**
 * Thrown when a method annotated with {@link DomainEventHandler} is declared
 * with more or less than 1 parameters.
 */
class TooManyParametersInEventHandlerException extends Throwable {

}
