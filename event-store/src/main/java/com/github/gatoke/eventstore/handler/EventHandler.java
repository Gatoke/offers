package com.github.gatoke.eventstore.handler;

import com.github.gatoke.eventstore.DomainEventHandler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.lang.reflect.Method;

import static lombok.AccessLevel.PROTECTED;

/**
 * Represents an event handler loaded into the runtime which is a method annotated with {@link DomainEventHandler}
 */
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class EventHandler {

    private String beanName;
    private String methodName;

    private transient Class<?> eventClass;

    EventHandler(final String beanName, final Method method) throws TooManyParametersInEventHandlerException {
        if (method.getParameterCount() != 1) {
            throw new TooManyParametersInEventHandlerException();
        }
        this.beanName = beanName;
        this.methodName = method.getName();
        this.eventClass = method.getParameters()[0].getType();
    }

    boolean isEligibleFor(final Class<?> eventClassName) {
        return this.eventClass.equals(eventClassName);
    }
}
