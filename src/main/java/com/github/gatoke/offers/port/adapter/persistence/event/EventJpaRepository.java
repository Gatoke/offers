package com.github.gatoke.offers.port.adapter.persistence.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

interface EventJpaRepository extends JpaRepository<PersistableEvent, UUID> {

    List<PersistableEvent> findFirst100ByOccurredOnAfterOrderByOccurredOnAsc(OffsetDateTime dateTime);

    @Query(value = "SELECT * FROM event ORDER BY occurred_on ASC LIMIT 100", nativeQuery = true)
    List<PersistableEvent> findPageFromBeginning();

}
