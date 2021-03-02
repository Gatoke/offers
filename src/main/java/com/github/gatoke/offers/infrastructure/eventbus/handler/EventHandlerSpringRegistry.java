package com.github.gatoke.offers.infrastructure.eventbus.handler;

import com.github.gatoke.offers.port.adapter.event.DomainEventHandler;
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

    private final Set<EventHandler> eventHandlers = new HashSet<>();

    @Override
    public Set<EventHandler> findAllFor(final Class<?> eventType) {
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.isEligibleFor(eventType))
                .collect(toUnmodifiableSet());
    }

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

    private Map<String, Set<Method>> determineEvenHandlers(final String[] beanDefinitionNames) {
        final Map<String, Set<Method>> eventHandlers = new HashMap<>();
        for (final String beanDefinitionName : beanDefinitionNames) {
            try {
                final Set<Method> methods = getEventHandlers(beanDefinitionName);
                eventHandlers.put(beanDefinitionName, methods);
            } catch (final ClassOfBeanCannotBeNullException e) {
                log.error("Loading event handlers failed during processing of bean: {}", beanDefinitionName);
                context.stop();
            }
        }
        return eventHandlers;
    }

    private Set<Method> getEventHandlers(final String beanName) throws ClassOfBeanCannotBeNullException {
        final Class<?> aClass = determineTargetClass(beanFactory, beanName);

        if (aClass == null) {
            throw new ClassOfBeanCannotBeNullException();
        }

        return selectMethods(
                aClass,
                (MetadataLookup<DomainEventHandler>) method -> findMergedAnnotation(method, DomainEventHandler.class)
        ).keySet();
    }
}
