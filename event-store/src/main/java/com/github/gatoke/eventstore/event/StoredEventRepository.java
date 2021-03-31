package com.github.gatoke.eventstore.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StoredEventRepository {

    private final StoredEventJpaRepository repository;

    public StoredEvent save(final StoredEvent event) {
        repository.save(event);
        return event;
    }

    public List<StoredEvent> getFromBeginning(final long limit) {
        return repository.getFromBeginning(limit);
    }

    public List<StoredEvent> getAfter(final UUID eventId, final long limit) {
        final long sequenceNumber = repository.findSequenceNumber(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        return repository.getAfter(sequenceNumber, limit);
    }
}
