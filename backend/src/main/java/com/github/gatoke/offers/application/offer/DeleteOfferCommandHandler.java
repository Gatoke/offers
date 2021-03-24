package com.github.gatoke.offers.application.offer;

import com.github.gatoke.offers.application.offer.command.DeleteOfferCommand;
import com.github.gatoke.offers.application.shared.CommandHandler;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class DeleteOfferCommandHandler implements CommandHandler<DeleteOfferCommand> {

    private final OfferRepository offerRepository;

    @Override
    public List<? extends DomainEvent> execute(final DeleteOfferCommand command) {
        final Offer offer = offerRepository.findOrFail(command.getOfferId());
        offer.delete();
        return offerRepository.save(offer).pickDomainEvents();
    }
}
