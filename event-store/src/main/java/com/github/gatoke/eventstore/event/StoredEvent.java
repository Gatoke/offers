package com.github.gatoke.eventstore.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Clock;
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

    @Column(columnDefinition = "serial", insertable = false)
    private Long sequence;

    private String type;

    private OffsetDateTime occurredOn;

    private String triggeredBy;

    private String payload;

    public StoredEvent(final String payload, final String eventType, final String triggeredBy) {
        this.id = UUID.randomUUID();
        this.type = eventType;
        this.occurredOn = OffsetDateTime.now(Clock.systemUTC());
        this.triggeredBy = triggeredBy;
        this.payload = payload;
    }
}
