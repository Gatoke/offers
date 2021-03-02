package com.github.gatoke.offers.infrastructure.eventbus;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.infrastructure.eventbus.model.EventHandlerProcess;
import com.github.gatoke.offers.port.adapter.persistence.event.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.String.format;
import static org.springframework.aop.support.AopUtils.getTargetClass;

@Slf4j
@Component
@RequiredArgsConstructor
class EventHandlerExecutor {

    private final EventMapper eventMapper;
    private final ApplicationContext context;

    @Transactional(propagation = Propagation.NESTED)
    void execute(final EventHandlerProcess handler) {
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
            handler.hold(format("No such handler: %s # %s", handler.getBeanName(), handler.getMethodName()));
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
