package com.github.gatoke.offers.port.adapter.eventhandler.user;

import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserRegisteredEventHandler {

    private final UserReadModelRepository userReadModelRepository;

    @Async
    @EventListener
    public void createUserReadModel(final UserRegisteredEvent event) {
        userReadModelRepository.createOn(event);
    }
}
