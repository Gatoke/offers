package com.github.gatoke.offers.eventstore.handler;

/**
 * Thrown when a method annotated with {@link com.github.gatoke.offers.eventstore.DomainEventHandler} is declared
 * with more or less than 1 parameters.
 */
class TooManyParametersInEventHandlerException extends Throwable {

}