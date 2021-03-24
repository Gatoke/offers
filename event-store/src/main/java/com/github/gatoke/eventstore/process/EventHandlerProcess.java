package com.github.gatoke.eventstore.process;

import com.github.gatoke.eventstore.event.StoredEvent;
import com.github.gatoke.eventstore.handler.EventHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

import static java.time.Clock.systemUTC;
import static java.time.OffsetDateTime.now;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "event_handler_process")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class EventHandlerProcess {

    private static final int DELAY_MULTIPLIER = 3;

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = PERSIST)
    @JoinColumn(name = "event_id")
    private StoredEvent event;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "beanName", column = @Column(name = "bean_name")),
            @AttributeOverride(name = "methodName", column = @Column(name = "method_name"))
    })
    private EventHandler eventHandler;

    @Enumerated(STRING)
    private EventHandlerProcessStatus status;

    private OffsetDateTime lastAttempt;

    private OffsetDateTime nextAttempt;

    private int attempts;

    private long delaySeconds;

    private String failReason;

    public EventHandlerProcess(final StoredEvent storedEvent, final EventHandler eventHandler) {
        this.id = UUID.randomUUID();
        this.event = storedEvent;
        this.eventHandler = eventHandler;
        this.status = EventHandlerProcessStatus.NEW;
        this.nextAttempt = now(systemUTC());
        this.attempts = 0;
        this.delaySeconds = 0;
    }

    public void begin() {
        if (EventHandlerProcessStatus.SUCCESS == this.status) {
            throw new IllegalStateException("Cannot begin when Handler already succeed.");
        }
        if (EventHandlerProcessStatus.RUNNING == this.status) {
            throw new IllegalStateException("Cannot begin when Handler is already in progress.");
        }
        this.status = EventHandlerProcessStatus.RUNNING;
        this.lastAttempt = now(systemUTC());
        this.attempts++;
    }

    public void success() {
        if (EventHandlerProcessStatus.RUNNING != this.status) {
            throw new IllegalStateException("Cannot finish when Handler is not running.");
        }
        this.status = EventHandlerProcessStatus.SUCCESS;
        this.nextAttempt = null;
    }

    public void hold(final String reason) {
        if (EventHandlerProcessStatus.SUCCESS == this.status) {
            throw new IllegalStateException("Cannot hold when Handler already succeed.");
        }
        this.status = EventHandlerProcessStatus.ON_HOLD;
        this.failReason = reason;
        this.nextAttempt = null;
    }

    public void failAndScheduleNextAttempt(final String reason) {
        if (EventHandlerProcessStatus.SUCCESS == this.status) {
            throw new IllegalStateException("Cannot fail when Handler already succeed.");
        }
        this.status = EventHandlerProcessStatus.FAILED;
        this.failReason = reason;
        scheduleNextAttempt();
    }

    private void scheduleNextAttempt() {
        if (delaySeconds == 0) {
            this.delaySeconds = 1;
        } else {
            this.delaySeconds *= DELAY_MULTIPLIER;
        }
        this.nextAttempt = this.nextAttempt.plusSeconds(this.delaySeconds);
    }
}
