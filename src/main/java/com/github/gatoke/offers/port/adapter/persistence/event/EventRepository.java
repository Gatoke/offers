package com.github.gatoke.offers.port.adapter.persistence.event;

import com.github.gatoke.offers.domain.shared.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class EventRepository {

    private final EventJpaRepository repository;
    private final EventMapper eventMapper;

    public void save(final Event<?> event) {
        final PersistableEvent persistableEvent = eventMapper.toPersistableEvent(event);
        repository.save(persistableEvent);
    }

    public List<EventDto> getPageFromBeginning() {
        return repository.findPageFromBeginning()
                .stream()
                .map(eventMapper::toEventDto)
                .collect(toList());
    }

    public List<EventDto> getPageAfterId(final UUID afterId) {
        final Optional<PersistableEvent> eventAfter = repository.findById(afterId);
        if (eventAfter.isPresent()) {
            return repository.findFirst100ByOccurredOnAfterOrderByOccurredOnAsc(eventAfter.get().getOccurredOn())
                    .stream()
                    .map(eventMapper::toEventDto)
                    .collect(toList());
        }
        throw new EventNotFoundException(afterId);
    }
}
