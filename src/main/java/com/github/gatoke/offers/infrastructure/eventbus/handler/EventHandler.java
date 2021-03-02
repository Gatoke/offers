package com.github.gatoke.offers.infrastructure.eventbus.handler;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@EqualsAndHashCode
public class EventHandler {

    private final String beanName;
    private final String methodName;
    private final Class<?> eventType;

    EventHandler(final String beanName, final Method method) throws TooManyParametersInEventHandlerException {
        if (method.getParameterCount() != 1) {
            throw new TooManyParametersInEventHandlerException();
        }
        this.beanName = beanName;
        this.methodName = method.getName();
        this.eventType = method.getParameters()[0].getType();
    }

    boolean isEligibleFor(final Class<?> eventType) {
        return this.eventType.equals(eventType);
    }
}
