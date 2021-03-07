package com.github.gatoke.offers.eventstore;

import com.github.gatoke.offers.eventstore.event.EventMapper;
import com.github.gatoke.offers.eventstore.event.StoredEvent;
import com.github.gatoke.offers.eventstore.handler.EventClassResolver;
import com.github.gatoke.offers.eventstore.handler.EventHandler;
import com.github.gatoke.offers.eventstore.process.EventHandlerProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

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
    private final EventClassResolver eventClassResolver;

    /**
     * Executes handler method for the process. Changes the status of the process depending on a result.
     * <p>
     * The {@link EventHandlerProcess} success when no exception is thrown.
     * <p>
     * The {@link EventHandlerProcess} holds when it cannot find the Method in a Bean. Can occur after renaming of
     * Class/Method where {@link DomainEventHandler} was declared.
     * <p>
     * The {@link EventHandlerProcess} fails when other exception is thrown. If it's thrown there is probably a problem
     * in {@link DomainEventHandler}.
     *
     * @param process - process to execute.
     * @see EventHandlerProcess
     * @see com.github.gatoke.offers.eventstore.process.EventHandlerProcessStatus
     */
    @Transactional(propagation = Propagation.NESTED)
    void execute(final EventHandlerProcess process) {
        final EventHandler handler = process.getEventHandler();

        try {
            process.begin();

            final StoredEvent event = process.getEvent();
            final Object deserializedPayload = eventMapper.toObject(
                    event.getPayload(),
                    eventClassResolver.resolveClass(event.getType())
            );

            invokeHandler(deserializedPayload, handler);

            process.success();
        } catch (final NoSuchMethodException ex) {
            log.warn("Processing event of type: {} and id: {} failed. Reason: No such handler: {} # {}",
                    handler.getEventClass(),
                    process.getEvent().getId(),
                    handler.getBeanName(), handler.getMethodName());
            process.hold(format("No such handler: %s # %s", handler.getBeanName(), handler.getMethodName()));
        } catch (final Exception ex) {
            log.warn("Processing event of type: {} and id: {} failed. Reason: {}",
                    handler.getEventClass(),
                    process.getEvent().getId(),
                    ex.getMessage());
            process.failAndScheduleNextAttempt(ex.getMessage());
        }
    }

    /**
     * @param event        Event as an Object (not String), loaded with a proper Class.
     * @param eventHandler Event handler to invoke. It's a method annotated with {@link DomainEventHandler}
     * @throws NoSuchMethodException     When a method name or bean name has changed.
     * @throws IllegalAccessException    When ReflectionUtils rejects to invoke method.
     * @throws InvocationTargetException When ReflectionUtils rejects to invoke method.
     */
    private void invokeHandler(final Object event, final EventHandler eventHandler)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final String beanName = eventHandler.getBeanName();
        final String methodName = eventHandler.getMethodName();

        final Object bean = context.getBean(beanName);
        final Method method = ReflectionUtils.findMethod(getTargetClass(bean), methodName, event.getClass());
        method.setAccessible(true);
        method.invoke(bean, event);
    }
}
