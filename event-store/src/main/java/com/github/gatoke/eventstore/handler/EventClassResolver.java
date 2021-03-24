package com.github.gatoke.eventstore.handler;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

/**
 * Provides a method to resolve a Class of an event for deserialization purposes.
 * You have to provide your own implementation depending on your needs.
 * The most safe way to do that is to create an Enum with event types pointing to their classes. This is safe when you rename package/class.
 * <p>
 * Example configuration:
 * <pre>
 * &#64;Configuration
 * class EventStoreConfiguration {
 *
 *     &#64;Bean
 *     EventClassResolver eventTypeResolver() {
 *         return new EventClassResolver(eventType -> EventType.valueOf(eventType).getTarget());
 *     }
 * }
 * </pre>
 * Example enum:
 * <pre>
 * &#64;Getter
 * &#64;RequiredArgsConstructor
 * public enum EventType {
 *
 *     USER_REGISTERED(UserRegisteredEvent.class);
 *
 *     private final Class<? extends DomainEvent> target;
 * }
 * </pre>
 */
public class EventClassResolver {

    private final Function<String, Class<?>> classResolver;

    /**
     * @param resolveClass function which takes event type as an input (String) and returns a Class on the output.
     * @see EventClassResolver example implementation
     */
    public EventClassResolver(@NotNull final Function<String, Class<?>> resolveClass) {
        this.classResolver = resolveClass;
    }

    public Class<?> resolveClass(final String eventType) {
        return classResolver.apply(eventType);
    }
}
