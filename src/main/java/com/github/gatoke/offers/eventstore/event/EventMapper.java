package com.github.gatoke.offers.eventstore.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Serialize/Deserialize methods for events.
 */
@Component
@RequiredArgsConstructor
public class EventMapper {

    private static final String PROCESSING_EVENT_TO_STRING_FAILED = "Processing event to string failed.";
    private static final String PROCESSING_EVENT_TO_OBJECT_FAILED = "Processing event to object failed.";

    private final ObjectMapper objectMapper;

    /**
     * Deserialize Event from String to Object with a proper type (Class).
     *
     * @param payload Event serialized as a String.
     * @param clazz   Target class to deserialize Event to.
     * @return Object representing an event
     */
    public Object toObject(final String payload, final Class<?> clazz) {
        try {
            return objectMapper.readValue(payload, clazz);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException(PROCESSING_EVENT_TO_OBJECT_FAILED);
        }
    }

    /**
     * Serialize Event from Object to String (JSON). Used for example to store Event as TEXT column in a database.
     *
     * @param event Object representing an event
     * @return String (JSON) representing an event.
     */
    public String toString(final Object event) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException(PROCESSING_EVENT_TO_STRING_FAILED);
        }
    }
}
