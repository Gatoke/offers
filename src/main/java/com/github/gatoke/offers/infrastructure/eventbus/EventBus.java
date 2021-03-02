package com.github.gatoke.offers.infrastructure.eventbus;

import com.github.gatoke.offers.infrastructure.eventbus.handler.EventHandler;
import com.github.gatoke.offers.infrastructure.eventbus.handler.EventHandlerRegistry;
import com.github.gatoke.offers.infrastructure.eventbus.model.EventHandlerProcess;
import com.github.gatoke.offers.infrastructure.eventbus.repository.EventHandlerProcessRepository;
import com.github.gatoke.offers.port.adapter.persistence.event.EventLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Service
@RequiredArgsConstructor
public class EventBus {

    private final EventHandlerProcessRepository repository;
    private final EventHandlerRegistry eventHandlerRegistry;
    private final EventHandlerExecutor eventHandlerExecutor;

    @Transactional(propagation = MANDATORY)
    public void register(final EventLog eventLog) {
        final Set<EventHandler> eventHandlers = eventHandlerRegistry.findAllFor(eventLog.getTargetClass());
        final List<EventHandlerProcess> processes = eventHandlers.stream()
                .map(eventHandler ->
                        new EventHandlerProcess(
                                eventLog,
                                eventHandler.getBeanName(),
                                eventHandler.getMethodName())
                )
                .collect(toList());

        repository.save(processes);
    }

    @Transactional
    public void processPendingEvents() {
        repository.getHandlersForProcessing()
                .forEach(eventHandlerExecutor::execute);
    }

    @Transactional
    public void cleanSucceedEvents() {
        repository.removeSucceedHandlers();
    }
}
