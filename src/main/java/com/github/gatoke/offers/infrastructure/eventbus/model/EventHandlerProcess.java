package com.github.gatoke.offers.infrastructure.eventbus.model;

import com.github.gatoke.offers.port.adapter.persistence.event.EventLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

import static java.time.Clock.systemUTC;
import static java.time.OffsetDateTime.now;
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

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private EventLog event;

    @NotBlank
    private String beanName;

    @NotBlank
    private String methodName;

    @Enumerated(STRING)
    private EventHandlerProcessStatus status;

    private OffsetDateTime lastAttempt;

    private OffsetDateTime nextAttempt;

    private int attempts;

    private long delaySeconds;

    private String failReason;

    public EventHandlerProcess(final EventLog event, final String beanName, final String methodName) {
        this.id = UUID.randomUUID();
        this.event = event;
        this.beanName = beanName;
        this.methodName = methodName;
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
