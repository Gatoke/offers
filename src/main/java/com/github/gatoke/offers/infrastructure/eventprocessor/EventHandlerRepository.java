package com.github.gatoke.offers.infrastructure.eventprocessor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.github.gatoke.offers.infrastructure.eventprocessor.HandlerStatus.SUCCESS;
import static java.time.Clock.systemUTC;
import static java.time.OffsetDateTime.now;

@Repository
@RequiredArgsConstructor
public class EventHandlerRepository {

    private static final List<HandlerStatus> STATUSES_FOR_PROCESSING = List.of(HandlerStatus.NEW, HandlerStatus.FAILED);

    private final EventHandlerJpaRepository repository;

    public void save(final List<EventHandler> eventHandlers) {
        repository.saveAll(eventHandlers);
    }

    Stream<EventHandler> getHandlersForProcessing() {
        return repository.findAllByStatusInAndNextAttemptIsNotNullAndNextAttemptBefore(
                STATUSES_FOR_PROCESSING,
                now(systemUTC()));
    }

    void removeSucceedHandlers() {
        repository.deleteAllByStatus(SUCCESS);
    }
}

interface EventHandlerJpaRepository extends JpaRepository<EventHandler, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Stream<EventHandler> findAllByStatusInAndNextAttemptIsNotNullAndNextAttemptBefore(List<HandlerStatus> status, OffsetDateTime before);

    void deleteAllByStatus(HandlerStatus status);
}
