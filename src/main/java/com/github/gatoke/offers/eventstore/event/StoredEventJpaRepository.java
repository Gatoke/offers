package com.github.gatoke.offers.eventstore.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface StoredEventJpaRepository extends JpaRepository<StoredEvent, UUID> {

    List<StoredEvent> findFirst100ByOccurredOnAfterOrderByOccurredOnAsc(OffsetDateTime dateTime);

    @Query(value = "SELECT * FROM event_log ORDER BY occurred_on ASC LIMIT 100", nativeQuery = true)
    List<StoredEvent> findPageFromBeginning();

}
