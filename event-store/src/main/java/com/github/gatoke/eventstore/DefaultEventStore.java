package com.github.gatoke.eventstore;

import com.github.gatoke.eventstore.event.EventMapper;
import com.github.gatoke.eventstore.event.StoredEvent;
import com.github.gatoke.eventstore.event.StoredEventRepository;
import com.github.gatoke.eventstore.handler.EventHandler;
import com.github.gatoke.eventstore.handler.EventHandlerRegistry;
import com.github.gatoke.eventstore.process.EventHandlerProcess;
import com.github.gatoke.eventstore.process.EventHandlerProcessRepository;
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
    private final StoredEventRepository storedEventRepository;
    private final EventHandlerRegistry eventHandlerRegistry;
    private final EventHandlerExecutor eventHandlerExecutor;
    private final EventMapper eventMapper;

    @Override
    @Transactional(propagation = MANDATORY)
    public void append(@NotNull final Object eventPayload, @NotNull final String eventType, final String triggeredBy) {
        final StoredEvent storedEvent = storedEventRepository.save(
                new StoredEvent(eventMapper.toString(eventPayload), eventType, triggeredBy)
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
