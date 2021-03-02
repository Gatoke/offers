package com.github.gatoke.offers.port.adapter.persistence.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.Time;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private static final String PROCESSING_EVENT_PAYLOAD_FAILED = "Processing event payload failed.";
    private static final String PROCESSING_TO_DOMAIN_EVENT_FAILED = "Processing to domain event failed.";

    private final ObjectMapper objectMapper;

    public DomainEvent<?> toDomainEvent(final EventLog event) {
        try {
            final Constructor<? extends DomainEvent> declaredConstructor = event.getTargetClass().getDeclaredConstructor();
            final DomainEvent<?> domainEvent = declaredConstructor.newInstance();
            writeField(domainEvent, "id", event.getId(), true);
            writeField(domainEvent, "occurredOn", Time.of(event.getOccurredOn()), true);
            writeField(domainEvent, "payload", toObjectPayload(event.getPayload(), event.getPayloadClass()), true);
            return domainEvent;
        } catch (final IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException ex) {
            throw new IllegalStateException(PROCESSING_TO_DOMAIN_EVENT_FAILED);
        }
    }

    EventLog toPersistableEvent(final DomainEvent<?> event) {
        return EventLog.builder()
                .id(event.getId())
                .type(event.getType())
                .occurredOn(event.getOccurredOn().getValue())
                .payload(toStringPayload(event.getPayload()))
                .build();
    }

    EventDto toEventDto(final EventLog eventLog) {
        return EventDto.builder()
                .id(eventLog.getId())
                .type(eventLog.getType())
                .occurredOn(eventLog.getOccurredOn())
                .payload(toObjectPayload(eventLog.getPayload()))
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

    private Object toObjectPayload(final String payload, final Class<?> payloadType) {
        try {
            return objectMapper.readValue(payload, payloadType);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException(PROCESSING_EVENT_PAYLOAD_FAILED);
        }
    }
}
