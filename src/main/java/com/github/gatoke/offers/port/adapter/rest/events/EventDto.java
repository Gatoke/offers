package com.github.gatoke.offers.port.adapter.rest.events;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
class EventDto {

    private final UUID id;
    private final String type;
    private final OffsetDateTime occurredOn;
    private final Object payload;
}
