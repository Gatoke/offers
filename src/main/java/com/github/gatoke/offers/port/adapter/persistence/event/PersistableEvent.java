package com.github.gatoke.offers.port.adapter.persistence.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class PersistableEvent {

    @Id
    private UUID id;

    private String type;

    private OffsetDateTime occurredOn;

    private String payload;

    static PersistableEvent of(final UUID id,
                               final String eventType,
                               final OffsetDateTime occurredOn,
                               final String payload) {
        final PersistableEvent persistableEvent = new PersistableEvent();
        persistableEvent.id = id;
        persistableEvent.type = eventType;
        persistableEvent.occurredOn = occurredOn;
        persistableEvent.payload = payload;
        return persistableEvent;
    }
}
