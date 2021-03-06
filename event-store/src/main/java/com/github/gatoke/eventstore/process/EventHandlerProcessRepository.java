package com.github.gatoke.eventstore.process;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

import static com.github.gatoke.eventstore.process.EventHandlerProcessStatus.SUCCESS;
import static java.time.Clock.systemUTC;
import static java.time.OffsetDateTime.now;

@Repository
@RequiredArgsConstructor
public class EventHandlerProcessRepository {

    private static final List<EventHandlerProcessStatus> STATUSES_FOR_PROCESSING =
            List.of(EventHandlerProcessStatus.NEW, EventHandlerProcessStatus.FAILED);

    private final EventHandlerProcessJpaRepository repository;

    public void save(final List<EventHandlerProcess> eventHandlers) {
        repository.saveAll(eventHandlers);
    }

    public Stream<EventHandlerProcess> getHandlersForProcessing() {
        return repository.findAllByStatusInAndNextAttemptIsNotNullAndNextAttemptBeforeOrderByNextAttemptAsc(
                STATUSES_FOR_PROCESSING,
                now(systemUTC()));
    }

    public void removeSucceedHandlers() {
        repository.deleteAllByStatus(SUCCESS);
    }
}
