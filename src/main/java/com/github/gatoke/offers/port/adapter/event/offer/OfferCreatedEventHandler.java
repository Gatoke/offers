package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.offers.domain.offer.event.OfferCreatedEvent;
import com.github.gatoke.offers.port.adapter.event.DomainEventHandler;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferCreatedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;

    @DomainEventHandler
    public void createOfferReadModel(final OfferCreatedEvent event) {
        offerReadModelRepository.create(event.getPayload());
    }
}
