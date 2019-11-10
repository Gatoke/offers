package com.github.gatoke.offers.port.adapter.eventhandler.offer;

import com.github.gatoke.offers.domain.offer.event.OfferDeletedEvent;
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
class OfferDeletedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;
    private final UserReadModelRepository userReadModelRepository;

    @Async
    @EventListener
    public void updateOfferReadModel(final OfferDeletedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getStatus());
        offerReadModelRepository.save(offerReadModel);
    }

    @Async
    @EventListener
    public void updateUserReadModel(final OfferDeletedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(valueOf(offerReadModel.getUserId()));
        userReadModel.decreaseActiveOffersCount();
        userReadModelRepository.save(userReadModel);
    }
}
