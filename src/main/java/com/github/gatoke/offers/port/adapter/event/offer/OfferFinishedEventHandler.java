package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.domain.offer.event.OfferFinishedEvent;
import com.github.gatoke.offers.port.adapter.event.DomainEventHandler;
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
    public void finishOffer(final OfferFinishedEvent event) {
        offerApplicationService.finishOn(event);
    }

    @DomainEventHandler
    public void updateOfferReadModel(final OfferFinishedEvent event) {
        final OfferFinishedEvent.Payload payload = event.getPayload();
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(payload.getOfferId());
        offerReadModel.setStatus(payload.getStatus());
        offerReadModelRepository.save(offerReadModel);
    }
}
