package com.github.gatoke.offers.port.adapter.rest.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gatoke.offers.port.adapter.persistence.event.PersistableEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventMapper {

    private final ObjectMapper objectMapper;

    EventDto toEventDto(final PersistableEvent persistableEvent) {
        return EventDto.builder()
                .id(persistableEvent.getId())
                .type(persistableEvent.getType())
                .occurredOn(persistableEvent.getOccurredOn())
                .payload(toObjectPayload(persistableEvent.getPayload()))
                .build();
    }

    @SneakyThrows
    private Object toObjectPayload(final String payload) {
        return objectMapper.readValue(payload, Object.class);
    }
}
