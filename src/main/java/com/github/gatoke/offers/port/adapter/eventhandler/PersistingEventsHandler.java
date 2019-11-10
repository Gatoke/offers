package com.github.gatoke.offers.port.adapter.eventhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.gatoke.offers.domain.shared.Event;
import com.github.gatoke.offers.port.adapter.persistence.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Component
@RequiredArgsConstructor
class PersistingEventsHandler {

    private final EventRepository eventRepository;

    @EventListener
    @Transactional(propagation = MANDATORY, rollbackFor = JsonProcessingException.class)
    public void saveEvent(final Event event) throws JsonProcessingException {
        eventRepository.save(event);
    }
}
