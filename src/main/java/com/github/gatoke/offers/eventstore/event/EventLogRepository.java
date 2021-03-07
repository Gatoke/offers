package com.github.gatoke.offers.eventstore.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventLogRepository {

    private final EventLogJpaRepository repository;

    public StoredEvent save(final StoredEvent event) {
        repository.save(event);
        return event;
    }

    public List<StoredEvent> getPageFromBeginning() {
        return repository.findPageFromBeginning();
    }

    public List<StoredEvent> getPageAfterId(final UUID afterId) {
        final Optional<StoredEvent> eventAfter = repository.findById(afterId);
        if (eventAfter.isPresent()) {
            return repository.findFirst100ByOccurredOnAfterOrderByOccurredOnAsc(eventAfter.get().getOccurredOn());
        }
        throw new EventNotFoundException(afterId);
    }
}
