package com.github.gatoke.offers.port.adapter.persistence.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventMapper {

    private static final String PROCESSING_EVENT_PAYLOAD_FAILED = "Processing event payload failed.";

    private final ObjectMapper objectMapper;

    PersistableEvent toPersistableEvent(final Event<?> event) {
        return PersistableEvent.builder()
                .id(event.getEventId())
                .type(event.getEventType())
                .occurredOn(event.getOccurredOn().getValue())
                .payload(toStringPayload(event.getPayload()))
                .build();
    }

    EventDto toEventDto(final PersistableEvent persistableEvent) {
        return EventDto.builder()
                .id(persistableEvent.getId())
                .type(persistableEvent.getType())
                .occurredOn(persistableEvent.getOccurredOn())
                .payload(toObjectPayload(persistableEvent.getPayload()))
                .build();
    }

    private String toStringPayload(final Object payload) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException(PROCESSING_EVENT_PAYLOAD_FAILED);
        }
    }

    private Object toObjectPayload(final String payload) {
        try {
            return objectMapper.readValue(payload, Object.class);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException(PROCESSING_EVENT_PAYLOAD_FAILED);
        }
    }
}
