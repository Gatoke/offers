package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.offers.application.offer.command.PublishOfferCommand;
import com.github.gatoke.offers.application.shared.CommandBus;
import com.github.gatoke.offers.domain.offer.event.OfferAcceptedEvent;
import com.github.gatoke.offers.eventstore.DomainEventHandler;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModel;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferAcceptedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;
    private final UserReadModelRepository userReadModelRepository;
    private final CommandBus commandBus;

    @DomainEventHandler
    void publishOffer(final OfferAcceptedEvent event) {
        final PublishOfferCommand command = new PublishOfferCommand(event.getOfferId());
        commandBus.execute(command);
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
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(offerReadModel.getUserId());
        userReadModel.increaseActiveOffersCount();
        userReadModelRepository.save(userReadModel);
    }
}
