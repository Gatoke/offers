package com.github.gatoke.offers.infrastructure.eventprocessor;

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
@Table(name = "event_handler")
@Getter
@NoArgsConstructor(access = PROTECTED)
class EventHandler {

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
    private HandlerStatus status;

    private OffsetDateTime lastAttempt;

    private OffsetDateTime nextAttempt;

    private int attempts;

    private long delaySeconds;

    private String reason;

    EventHandler(final EventLog event, final String beanName, final String methodName) {
        this.id = UUID.randomUUID();
        this.event = event;
        this.beanName = beanName;
        this.methodName = methodName;
        this.status = HandlerStatus.NEW;
        this.nextAttempt = now(systemUTC());
        this.attempts = 0;
        this.delaySeconds = 0;
    }

    void begin() {
        this.lastAttempt = now(systemUTC());
        this.attempts++;
    }

    void success() {
        this.status = HandlerStatus.SUCCESS;
        this.nextAttempt = null;
    }

    void holdOn(final String reason) {
        this.status = HandlerStatus.ON_HOLD;
        this.reason = reason;
        this.nextAttempt = null;
    }

    void failAndScheduleNextAttempt(final String reason) {
        this.status = HandlerStatus.FAILED;
        this.reason = reason;
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
