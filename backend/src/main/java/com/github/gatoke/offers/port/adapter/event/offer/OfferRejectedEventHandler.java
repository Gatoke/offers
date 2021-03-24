package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.eventstore.DomainEventHandler;
import com.github.gatoke.offers.domain.offer.event.OfferRejectedEvent;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferRejectedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;

    @DomainEventHandler
    void updateOfferReadModel(final OfferRejectedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getOfferStatus());
        offerReadModel.setRejectedReason(event.getReason());
        offerReadModelRepository.save(offerReadModel);
    }
}
