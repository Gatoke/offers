package com.github.gatoke.offers.domain.shared;

public interface EventPublisher {

    void publish(DomainEvent<?> event);
}
