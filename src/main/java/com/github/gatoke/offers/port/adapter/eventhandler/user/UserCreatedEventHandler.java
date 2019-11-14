package com.github.gatoke.offers.port.adapter.eventhandler.user;

import com.github.gatoke.offers.domain.user.event.UserCreatedEvent;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserCreatedEventHandler {

    private final UserReadModelRepository userReadModelRepository;

    @Async
    @EventListener
    public void createUserReadModel(final UserCreatedEvent event) {
        userReadModelRepository.createOn(event);
    }
}
