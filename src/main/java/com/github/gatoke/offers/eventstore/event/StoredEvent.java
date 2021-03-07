package com.github.gatoke.offers.eventstore.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "event_log")
@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class StoredEvent {

    @Id
    private UUID id;

    private String type;

    private OffsetDateTime occurredOn;

    private String payload;

    public StoredEvent(final String payload, final String eventType) {
        this.id = UUID.randomUUID();
        this.type = eventType;
        this.occurredOn = OffsetDateTime.now();
        this.payload = payload;
    }
}
