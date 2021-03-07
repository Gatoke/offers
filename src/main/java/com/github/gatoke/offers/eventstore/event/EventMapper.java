package com.github.gatoke.offers.eventstore.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private static final String PROCESSING_EVENT_TO_STRING_FAILED = "Processing event to string failed.";
    private static final String PROCESSING_EVENT_TO_OBJECT_FAILED = "Processing event to object failed.";

    private final ObjectMapper objectMapper;

    public Object toObject(final String payload, final Class<?> clazz) {
        try {
            return objectMapper.readValue(payload, clazz);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException(PROCESSING_EVENT_TO_OBJECT_FAILED);
        }
    }

    public String toString(final Object event) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException(PROCESSING_EVENT_TO_STRING_FAILED);
        }
    }
}
