package com.github.gatoke.eventstore.process;

public enum EventHandlerProcessStatus {

    NEW,
    FAILED,
    ON_HOLD,
    RUNNING,
    SUCCESS
}
