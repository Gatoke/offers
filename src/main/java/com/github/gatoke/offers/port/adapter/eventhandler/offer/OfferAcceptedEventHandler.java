package com.github.gatoke.offers.port.adapter.eventhandler.offer;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.domain.offer.event.OfferAcceptedEvent;
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
class OfferAcceptedEventHandler {

    private final OfferApplicationService offerApplicationService;
    private final OfferReadModelRepository offerReadModelRepository;
    private final UserReadModelRepository userReadModelRepository;

    @Async
    @EventListener
    public void publishOffer(final OfferAcceptedEvent event) {
        offerApplicationService.publishOn(event);
    }

    @Async
    @EventListener
    public void updateOfferReadModel(final OfferAcceptedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getStatus());
        offerReadModelRepository.save(offerReadModel);
    }

    @Async
    @EventListener
    public void updateUserReadModel(final OfferAcceptedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(valueOf(offerReadModel.getUserId()));
        userReadModel.increaseActiveOffersCount();
        userReadModelRepository.save(userReadModel);
    }
}
