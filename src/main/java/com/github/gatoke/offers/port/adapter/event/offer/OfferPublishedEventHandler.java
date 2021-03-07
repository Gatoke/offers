package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.offers.domain.offer.event.OfferPublishedEvent;
import com.github.gatoke.offers.eventstore.DomainEventHandler;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferPublishedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;

    @DomainEventHandler
    void updateOfferReadModel(final OfferPublishedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getOfferStatus());
        offerReadModelRepository.save(offerReadModel);
    }
}
