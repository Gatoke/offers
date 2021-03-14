package com.github.gatoke.offers.port.adapter.configuration;

import com.github.gatoke.offers.domain.shared.EventType;
import com.github.gatoke.offers.eventstore.handler.EventClassResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EventStoreConfiguration {

    @Bean
    EventClassResolver eventClassResolver() {
        return new EventClassResolver(eventType -> EventType.valueOf(eventType).getTarget());
    }
}
