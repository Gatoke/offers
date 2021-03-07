package com.github.gatoke.offers.port.adapter.rest.events;

import com.github.gatoke.offers.eventstore.event.StoredEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class EventDto {

    private final UUID id;
    private final String type;
    private final OffsetDateTime occurredOn;
    private final Object payload;

    public static EventDto of(final StoredEvent storedEvent) {
        return EventDto.builder()
                .id(storedEvent.getId())
                .type(storedEvent.getType())
                .occurredOn(storedEvent.getOccurredOn())
                .payload(storedEvent.getPayload())
                .build();
    }
}
