package com.github.gatoke.offers.port.adapter.configuration;

import com.github.gatoke.offers.domain.shared.Event;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DefaultEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishEvent(final Event event) {
        publisher.publishEvent(event);
    }
}
