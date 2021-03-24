package com.github.gatoke.offers.port.adapter.event.offer;

import com.github.gatoke.eventstore.DomainEventHandler;
import com.github.gatoke.offers.application.offer.command.FinishOfferCommand;
import com.github.gatoke.offers.application.shared.CommandBus;
import com.github.gatoke.offers.domain.offer.event.OfferFinishedEvent;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferFinishedEventHandler {

    private final OfferReadModelRepository offerReadModelRepository;
    private final CommandBus commandBus;

    @DomainEventHandler
    void finishOffer(final OfferFinishedEvent event) {
        final FinishOfferCommand command = new FinishOfferCommand(event.getOfferId());
        commandBus.execute(command);
    }

    @DomainEventHandler
    void updateOfferReadModel(final OfferFinishedEvent event) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(event.getOfferId());
        offerReadModel.setStatus(event.getOfferStatus());
        offerReadModelRepository.save(offerReadModel);
    }
}
