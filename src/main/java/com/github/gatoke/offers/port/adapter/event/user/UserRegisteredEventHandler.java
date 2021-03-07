package com.github.gatoke.offers.port.adapter.event.user;

import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
import com.github.gatoke.offers.eventstore.DomainEventHandler;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserRegisteredEventHandler {

    private final UserReadModelRepository userReadModelRepository;

    @DomainEventHandler
    public void createUserReadModel(final UserRegisteredEvent event) {
        userReadModelRepository.create(event);
    }
}
