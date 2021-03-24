package com.github.gatoke.offers.application.offer;

import com.github.gatoke.offers.application.offer.command.ExpireOfferCommand;
import com.github.gatoke.offers.application.shared.CommandHandler;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ExpireOfferCommandHandler implements CommandHandler<ExpireOfferCommand> {

    private final OfferRepository offerRepository;

    @Override
    public List<? extends DomainEvent> execute(final ExpireOfferCommand command) {
        final Offer offer = offerRepository.findOrFail(command.getOfferId());
        offer.expire();
        return offerRepository.save(offer).pickDomainEvents();
    }
}
