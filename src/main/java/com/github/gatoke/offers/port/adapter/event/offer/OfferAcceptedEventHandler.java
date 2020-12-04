package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.domain.offer.event.OfferAcceptedEvent;
import com.github.gatoke.offers.port.adapter.event.DomainEventHandler;
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
    public void publishOffer(final OfferAcceptedEvent event) {
        offerApplicationService.publishOn(event);
    }

    @DomainEventHandler
    public void updateOfferReadModel(final OfferAcceptedEvent event) {
        final OfferAcceptedEvent.Payload payload = event.getPayload();
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(payload.getOfferId());
        offerReadModel.setStatus(payload.getStatus());
        offerReadModelRepository.save(offerReadModel);
    }

    @DomainEventHandler
    public void updateUserReadModel(final OfferAcceptedEvent event) {
        final OfferAcceptedEvent.Payload payload = event.getPayload();
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(payload.getOfferId());
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(valueOf(offerReadModel.getUserId()));
        userReadModel.increaseActiveOffersCount();
        userReadModelRepository.save(userReadModel);
    }
}
