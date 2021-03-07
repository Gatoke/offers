package com.github.gatoke.offers.eventstore;

import com.github.gatoke.offers.eventstore.event.EventLogRepository;
import com.github.gatoke.offers.eventstore.event.EventMapper;
import com.github.gatoke.offers.eventstore.event.StoredEvent;
import com.github.gatoke.offers.eventstore.handler.EventHandler;
import com.github.gatoke.offers.eventstore.handler.EventHandlerRegistry;
import com.github.gatoke.offers.eventstore.process.EventHandlerProcess;
import com.github.gatoke.offers.eventstore.process.EventHandlerProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Service
@RequiredArgsConstructor
class DefaultEventStore implements EventStore {

    private final EventHandlerProcessRepository processRepository;
    private final EventLogRepository eventLogRepository;
    private final EventHandlerRegistry eventHandlerRegistry;
    private final EventHandlerExecutor eventHandlerExecutor;
    private final EventMapper eventMapper;

    @Override
    @Transactional(propagation = MANDATORY)
    public void append(@NotNull final Object event, @NotNull final String eventType) {
        final StoredEvent storedEvent = eventLogRepository.save(
                new StoredEvent(eventMapper.toString(event), eventType)
        );

        final Set<EventHandler> eventHandlers = eventHandlerRegistry.findAllBy(eventType);
        final List<EventHandlerProcess> processes = eventHandlers.stream()
                .map(eventHandler -> new EventHandlerProcess(storedEvent, eventHandler))
                .collect(toList());

        processRepository.save(processes);
    }

    @Override
    @Transactional
    public void processPendingEvents() {
        processRepository.getHandlersForProcessing()
                .forEach(eventHandlerExecutor::execute);
    }

    @Override
    @Transactional
    public void cleanSucceedProcesses() {
        processRepository.removeSucceedHandlers();
    }
}
