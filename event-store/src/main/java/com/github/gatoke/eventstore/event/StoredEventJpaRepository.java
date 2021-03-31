package com.github.gatoke.eventstore.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoredEventJpaRepository extends JpaRepository<StoredEvent, UUID> {

    @Query(value = "SELECT e.sequence FROM StoredEvent e WHERE e.id = :eventId")
    Optional<Long> findSequenceNumber(@Param("eventId") UUID eventId);

    @Query(value = "SELECT * FROM event_log ORDER BY sequence ASC LIMIT :limit", nativeQuery = true)
    List<StoredEvent> getFromBeginning(@Param("limit") long limit);

    @Query(value = "SELECT * FROM event_log WHERE sequence > :sequence ORDER BY sequence ASC LIMIT :limit", nativeQuery = true)
    List<StoredEvent> getAfter(@Param("sequence") long sequence, @Param("limit") long limit);
}
