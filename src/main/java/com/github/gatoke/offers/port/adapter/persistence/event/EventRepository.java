package com.github.gatoke.offers.port.adapter.persistence.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepository {

    private final EventJpaRepository repository;
    private final ObjectMapper objectMapper;

    public void save(final Event<?> event) throws JsonProcessingException {
        final String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event.getPayload());
        final PersistableEvent persistableEvent = PersistableEvent.of(event.getEventId(),
                                                                      event.getEventType(),
                                                                      event.getOccurredOn().getValue(),
                                                                      payload);
        repository.save(persistableEvent);
    }
}
