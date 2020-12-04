package com.github.gatoke.offers.port.adapter.event;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import com.github.gatoke.offers.infrastructure.eventprocessor.EventProcessor;
import com.github.gatoke.offers.port.adapter.persistence.event.EventLog;
import com.github.gatoke.offers.port.adapter.persistence.event.EventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Component
@RequiredArgsConstructor
class StoreAndForwardEventPublisher implements EventPublisher {

    private final EventLogRepository eventLogRepository;
    private final EventProcessor eventProcessor;

    @Override
    @Transactional(propagation = MANDATORY)
    public void publish(final DomainEvent event) {
        final EventLog savedEvent = eventLogRepository.save(event);
        eventProcessor.register(savedEvent);
    }

    @Scheduled(fixedDelayString = "1000")
    public void forward() {
        eventProcessor.processPendingEvents();
    }

    @Scheduled(cron = "0 1 1 * * *", zone = "UTC")
    public void clean() {
        eventProcessor.cleanSucceedEvents();
    }
}
