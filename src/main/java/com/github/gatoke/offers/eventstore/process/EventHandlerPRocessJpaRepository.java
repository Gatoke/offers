package com.github.gatoke.offers.eventstore.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

interface EventHandlerProcessJpaRepository extends JpaRepository<EventHandlerProcess, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Stream<EventHandlerProcess> findAllByStatusInAndNextAttemptIsNotNullAndNextAttemptBeforeOrderByNextAttemptAsc(
            List<EventHandlerProcessStatus> status, OffsetDateTime before
    );

    void deleteAllByStatus(EventHandlerProcessStatus status);
}

