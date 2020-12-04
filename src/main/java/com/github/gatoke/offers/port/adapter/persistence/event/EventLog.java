package com.github.gatoke.offers.port.adapter.persistence.event;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "event_log")
@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class EventLog {

    @Id
    private UUID id;

    @Enumerated(STRING)
    private EventType type;

    private OffsetDateTime occurredOn;

    private String payload;

    Class<? extends DomainEvent> getTargetClass() {
        return type.getTarget();
    }

    Class<?> getPayloadClass() {
        return type.getTargetPayload();
    }
}
