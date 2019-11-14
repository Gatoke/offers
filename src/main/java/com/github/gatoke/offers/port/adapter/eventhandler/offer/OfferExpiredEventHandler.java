package com.github.gatoke.offers.port.adapter.eventhandler.offer;

import com.github.gatoke.offers.domain.offer.event.OfferExpiredEvent;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModel;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;

@Component
@RequiredArgsConstructor
class OfferExpiredEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;
    private final UserReadModelRepository userReadModelRepository;

    @Async
    @EventListener
    public void updateOfferReadModel(final OfferExpiredEvent event) {
        final OfferExpiredEvent.Payload payload = event.getPayload();
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(payload.getOfferId());
        offerReadModel.setStatus(payload.getStatus());
        offerReadModelRepository.save(offerReadModel);
    }

    @Async
    @EventListener
    public void updateUserReadModel(final OfferExpiredEvent event) {
        final OfferExpiredEvent.Payload payload = event.getPayload();
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(payload.getOfferId());
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(valueOf(offerReadModel.getUserId()));
        userReadModel.decreaseActiveOffersCount();
        userReadModelRepository.save(userReadModel);
    }
}
