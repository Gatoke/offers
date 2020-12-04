package com.github.gatoke.offers.port.adapter.persistence.event;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class EventLogRepository {

    private final EventLogJpaRepository repository;
    private final EventMapper eventMapper;

    public EventLog save(final DomainEvent<?> event) {
        final EventLog eventLog = eventMapper.toPersistableEvent(event);
        repository.save(eventLog);
        return eventLog;
    }

    public List<EventDto> getPageFromBeginning() {
        return repository.findPageFromBeginning()
                .stream()
                .map(eventMapper::toEventDto)
                .collect(toList());
    }

    public List<EventDto> getPageAfterId(final UUID afterId) {
        final Optional<EventLog> eventAfter = repository.findById(afterId);
        if (eventAfter.isPresent()) {
            return repository.findFirst100ByOccurredOnAfterOrderByOccurredOnAsc(eventAfter.get().getOccurredOn())
                    .stream()
                    .map(eventMapper::toEventDto)
                    .collect(toList());
        }
        throw new EventNotFoundException(afterId);
    }
}
