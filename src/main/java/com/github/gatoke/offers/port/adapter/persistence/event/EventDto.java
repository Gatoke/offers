package com.github.gatoke.offers.port.adapter.persistence.event;

import com.github.gatoke.offers.domain.shared.EventType;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class EventDto {

    private final UUID id;
    private final EventType type;
    private final OffsetDateTime occurredOn;
    private final Object payload;
}
