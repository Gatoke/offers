package com.github.gatoke.offers.port.adapter.event;

import com.github.gatoke.eventstore.EventStore;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import com.github.gatoke.offers.port.adapter.rest.OperationPerformerProvider;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Component
@RequiredArgsConstructor
class StoreAndForwardEventPublisher implements EventPublisher {

    private final EventStore eventStore;
    private final OperationPerformerProvider operationPerformerProvider;

    @Override
    @Transactional(propagation = MANDATORY)
    public void publish(final DomainEvent event) {
        eventStore.append(
                event,
                event.getEventType().toString(),
                operationPerformerProvider.getOperationPerformer()
        );
    }

    @Scheduled(fixedDelayString = "PT1S")
    @SchedulerLock(name = "processPendingEventsScheduler", lockAtMostFor = "5M")
    public void forward() {
        eventStore.processPendingEvents();
    }

    @Scheduled(cron = "0 1 1 * * *", zone = "UTC")
    @SchedulerLock(name = "cleanSucceedEventsScheduler", lockAtMostFor = "60M")
    public void clean() {
        eventStore.cleanSucceedProcesses();
    }
}
