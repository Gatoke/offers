package com.github.gatoke.offers.port.adapter.eventhandler.offer;

import com.github.gatoke.offers.domain.offer.event.OfferCreatedEvent;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferCreatedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;

    @Async
    @EventListener
    public void createOfferReadModel(final OfferCreatedEvent event) {
        offerReadModelRepository.create(event.getPayload().getOffer());
    }
}
