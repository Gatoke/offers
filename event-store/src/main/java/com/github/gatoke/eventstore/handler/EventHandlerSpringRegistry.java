package com.github.gatoke.eventstore.handler;

import com.github.gatoke.eventstore.DomainEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.MethodIntrospector.MetadataLookup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.springframework.aop.framework.autoproxy.AutoProxyUtils.determineTargetClass;
import static org.springframework.core.MethodIntrospector.selectMethods;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Component
@Slf4j
@RequiredArgsConstructor
class EventHandlerSpringRegistry implements EventHandlerRegistry {

    private final ConfigurableApplicationContext context;
    private final ConfigurableListableBeanFactory beanFactory;
    private final EventClassResolver eventClassResolver;

    private final Set<EventHandler> eventHandlers = new HashSet<>();

    @Override
    public Set<EventHandler> findAllBy(final String eventType) {
        final Class<?> eventClass = eventClassResolver.resolveClass(eventType);
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.isEligibleFor(eventClass))
                .collect(toUnmodifiableSet());
    }

    /**
     * Executed on application's startup.
     * Goes through Spring's context and looks for methods annotated with {@link DomainEventHandler}.
     * Creates a collection of {@link EventHandler}.
     * <p>
     * In case of exception occurs, the application stops.
     */
    @PostConstruct
    protected void loadEventHandlers() {
        final String[] beanDefinitionNames = context.getBeanDefinitionNames();
        final Map<String, Set<Method>> eventHandlers = determineEvenHandlers(beanDefinitionNames);

        for (final Map.Entry<String, Set<Method>> entry : eventHandlers.entrySet()) {
            final String beanName = entry.getKey();
            final Set<Method> handlerMethods = entry.getValue();

            for (final Method handlerMethod : handlerMethods) {
                try {
                    final EventHandler eventHandler = new EventHandler(beanName, handlerMethod);
                    this.eventHandlers.add(eventHandler);
                } catch (final TooManyParametersInEventHandlerException e) {
                    log.error(
                            "Loading event handlers failed. Event handler should have only one declared parameter: {} # {}",
                            beanName, handlerMethod.getName()
                    );
                    context.stop();
                }
            }
        }
    }

    /**
     * @param beanDefinitionNames all beans loaded into IoC container
     * @return Map of {BeanName, Event Handlers}
     */
    private Map<String, Set<Method>> determineEvenHandlers(final String[] beanDefinitionNames) {
        final Map<String, Set<Method>> eventHandlers = new HashMap<>();
        for (final String beanDefinitionName : beanDefinitionNames) {
            final Set<Method> methods = getEventHandlers(beanDefinitionName);
            eventHandlers.put(beanDefinitionName, methods);
        }
        return eventHandlers;
    }

    /**
     * Looking for methods annotated with {@link DomainEventHandler} within a Bean.
     * Current implementation avoids loading Bean by context.getBean() to prevent Circular Dependency in IoC Spring's container.
     * <p>
     * Inspired by {@link org.springframework.context.event.EventListenerMethodProcessor}
     *
     * @param beanName Name of a Bean in IoC container.
     * @return list of event handlers declared in a Bean.
     */
    private Set<Method> getEventHandlers(final String beanName) {
        final Class<?> aClass = determineTargetClass(beanFactory, beanName);

        if (aClass == null) {
            log.error("Loading event handlers failed during processing of bean: {}", beanName);
            context.stop();
        }

        return selectMethods(
                requireNonNull(aClass),
                (MetadataLookup<DomainEventHandler>) method -> findMergedAnnotation(method, DomainEventHandler.class)
        ).keySet();
    }
}
