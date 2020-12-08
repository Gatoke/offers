package com.github.gatoke.offers.infrastructure.eventprocessor;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import com.github.gatoke.offers.port.adapter.persistence.event.EventLog;
import com.github.gatoke.offers.port.adapter.persistence.event.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.aop.support.AopUtils.getTargetClass;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventProcessor {

    private final EventMapper eventMapper;
    private final EventHandlerRepository repository;
    private final EventHandlersResolver eventHandlersResolver;
    private final ApplicationContext context;

    @Transactional(propagation = MANDATORY)
    public void register(final EventLog eventLog) {
        final EventType eventType = eventLog.getType();
        final List<EventHandler> eventHandlers = eventHandlersResolver.resolveBeanAndMethod(eventType)
                .stream()
                .map(beanAndMethod -> new EventHandler(eventLog, beanAndMethod.getFirst(), beanAndMethod.getSecond()))
                .collect(toList());
        repository.save(eventHandlers);
    }

    @Transactional
    public void processPendingEvents() {
        repository.getHandlersForProcessing()
                .forEach(this::process);
    }

    @Transactional
    public void cleanSucceedEvents() {
        repository.removeSucceedHandlers();
    }

    private void process(final EventHandler handler) {
        try {
            handler.begin();

            final DomainEvent<?> domainEvent = eventMapper.toDomainEvent(handler.getEvent());
            invokeHandler(domainEvent, handler.getBeanName(), handler.getMethodName());

            handler.success();
        } catch (final NoSuchMethodException ex) {
            log.warn("Processing event of type: {} and id: {} failed. Reason: No such handler: {} # {}",
                    handler.getEvent().getType(),
                    handler.getEvent().getId(),
                    handler.getBeanName(), handler.getMethodName());
            handler.holdOn(format("No such handler: %s # %s", handler.getBeanName(), handler.getMethodName()));
        } catch (final Exception ex) {
            log.warn("Processing event of type: {} and id: {} failed. Reason: {}",
                    handler.getEvent().getType(),
                    handler.getEvent().getId(),
                    ex.getMessage());
            handler.failAndScheduleNextAttempt(ex.getMessage());
        }
    }

    private void invokeHandler(final DomainEvent<?> domainEvent, final String beanName, final String methodName)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Object bean = context.getBean(beanName);
        final Method method = getTargetClass(bean).getMethod(methodName, domainEvent.getClass());
        method.setAccessible(true);
        method.invoke(bean, domainEvent);
    }
}
