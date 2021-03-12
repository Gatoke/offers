package com.github.gatoke.offers.application.offer;

import com.github.gatoke.offers.application.offer.command.PublishOfferCommand;
import com.github.gatoke.offers.application.shared.CommandHandler;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class PublishOfferCommandHandler implements CommandHandler<PublishOfferCommand> {

    private final OfferRepository offerRepository;

    @Override
    public List<? extends DomainEvent> execute(final PublishOfferCommand command) {
        final Offer offer = offerRepository.findOrFail(command.getOfferId());
        offer.publish();
        return offerRepository.save(offer).pickDomainEvents();
    }
}
