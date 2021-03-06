package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.eventstore.DomainEventHandler;
import com.github.gatoke.offers.domain.offer.event.OfferDeletedEvent;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModel;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferDeletedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;
    private final UserReadModelRepository userReadModelRepository;

    @DomainEventHandler
    void updateOfferReadModel(final OfferDeletedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getOfferStatus());
        offerReadModelRepository.save(offerReadModel);
    }

    @DomainEventHandler
    void updateUserReadModel(final OfferDeletedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(offerReadModel.getUserId());
        userReadModel.decreaseActiveOffersCount();
        userReadModelRepository.save(userReadModel);
    }
}
