package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.domain.offer.event.OfferAcceptedEvent;
import com.github.gatoke.offers.eventstore.DomainEventHandler;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModel;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;

@Component
@RequiredArgsConstructor
class OfferAcceptedEventHandler {

    private final OfferApplicationService offerApplicationService;
    private final OfferReadModelRepository offerReadModelRepository;
    private final UserReadModelRepository userReadModelRepository;

    @DomainEventHandler
    void publishOffer(final OfferAcceptedEvent event) {
        offerApplicationService.publishOn(event);
    }

    @DomainEventHandler
    void updateOfferReadModel(final OfferAcceptedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getOfferStatus());
        offerReadModelRepository.save(offerReadModel);
    }

    @DomainEventHandler
    void updateUserReadModel(final OfferAcceptedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(valueOf(offerReadModel.getUserId()));
        userReadModel.increaseActiveOffersCount();
        userReadModelRepository.save(userReadModel);
    }
}
