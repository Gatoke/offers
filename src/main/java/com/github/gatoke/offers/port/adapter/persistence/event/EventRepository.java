package com.github.gatoke.offers.port.adapter.persistence.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gatoke.offers.domain.shared.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventRepository {

    private final EventJpaRepository repository;
    private final ObjectMapper objectMapper;

    public void save(final Event<?> event) throws JsonProcessingException {
        final String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event.getPayload());
        final PersistableEvent persistableEvent = PersistableEvent.of(
                event.getEventId(),
                event.getEventType(),
                event.getOccurredOn().getValue(),
                payload
        );
        repository.save(persistableEvent);
    }

    public List<PersistableEvent> getPageFromBeginning() {
        return repository.findPageFromBeginning();
    }

    public List<PersistableEvent> getPageAfterId(final UUID afterId) {
        final Optional<PersistableEvent> eventAfter = repository.findById(afterId);
        if (eventAfter.isPresent()) {
            return repository.findFirst100ByOccurredOnAfterOrderByOccurredOnAsc(eventAfter.get().getOccurredOn());
        }
        throw new EventNotFoundException(afterId);
    }
}
