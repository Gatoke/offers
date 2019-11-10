package com.github.gatoke.offers.port.adapter.eventhandler.offer;

import com.github.gatoke.offers.domain.offer.event.OfferPublishedEvent;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferPublishedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;

    @Async
    @EventListener
    public void updateOfferReadModel(final OfferPublishedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getStatus());
        offerReadModelRepository.save(offerReadModel);
    }
}
