package com.github.gatoke.offers.port.adapter.eventhandler.offer;

import com.github.gatoke.offers.domain.offer.event.OfferRejectedEvent;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferRejectedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;

    @Async
    @EventListener
    public void updateOfferReadModel(final OfferRejectedEvent event) {
        final OfferRejectedEvent.Payload payload = event.getPayload();
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(payload.getOfferId());
        offerReadModel.setStatus(payload.getStatus());
        offerReadModel.setRejectedReason(payload.getReason());
        offerReadModelRepository.save(offerReadModel);
    }
}
