package com.github.gatoke.offers.infrastructure.eventprocessor;

import com.github.gatoke.offers.domain.shared.EventType;
import com.github.gatoke.offers.port.adapter.event.DomainEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.springframework.aop.support.AopUtils.getTargetClass;

@Service
@RequiredArgsConstructor
public class EventHandlersResolver {

    private final ApplicationContext context;

    List<Pair<String, String>> resolveBeanAndMethod(final EventType eventType) {
        final List<Pair<String, String>> beanAndMethod = new ArrayList<>();

        for (final String beanName : context.getBeanDefinitionNames()) {
            final Method[] declaredMethods = getDeclaredMethods(beanName);
            stream(declaredMethods)
                    .filter(method -> isEventHandlerForType(method, eventType))
                    .forEach(method -> beanAndMethod.add(Pair.of(beanName, method.getName())));
        }
        return beanAndMethod;
    }

    private Method[] getDeclaredMethods(final String beanName) {
        return getTargetClass(context.getBean(beanName)).getDeclaredMethods();
    }

    private boolean isEventHandlerForType(final Method method, final EventType eventType) {
        return method.isAnnotationPresent(DomainEventHandler.class)
                && hasParameterOfType(method, eventType.getTarget());
    }

    private boolean hasParameterOfType(final Method method, final Class eventClass) {
        return asList(method.getParameterTypes()).contains(eventClass);
    }
}
