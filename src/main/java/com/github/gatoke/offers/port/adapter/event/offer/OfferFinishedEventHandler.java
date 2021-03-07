package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.domain.offer.event.OfferFinishedEvent;
import com.github.gatoke.offers.eventstore.DomainEventHandler;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferFinishedEventHandler {

    private final OfferApplicationService offerApplicationService;
    private final OfferReadModelRepository offerReadModelRepository;

    @DomainEventHandler
    void finishOffer(final OfferFinishedEvent event) {
        offerApplicationService.finishOn(event);
    }

    @DomainEventHandler
    void updateOfferReadModel(final OfferFinishedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getOfferStatus());
        offerReadModelRepository.save(offerReadModel);
    }
}
