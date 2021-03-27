package com.github.gatoke.offers.domain.shared;

import java.beans.Transient;

public interface DomainEvent {

    @Transient
    EventType getEventType();
}
