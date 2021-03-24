package com.github.gatoke.offers.port.adapter.rest.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gatoke.eventstore.event.StoredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventDtoMapper {

    private final ObjectMapper objectMapper;

    EventDto toEventDto(final StoredEvent storedEvent) {
        return EventDto.builder()
                .id(storedEvent.getId())
                .type(storedEvent.getType())
                .occurredOn(storedEvent.getOccurredOn())
                .payload(extractPayload(storedEvent))
                .build();
    }

    private Object extractPayload(final StoredEvent storedEvent) {
        try {
            return objectMapper.readValue(storedEvent.getPayload(), Object.class);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize event payload into object. id: " + storedEvent.getId());
        }
    }
}
