package com.github.gatoke.offers.application.offer;

import com.github.gatoke.offers.application.offer.command.RejectOfferCommand;
import com.github.gatoke.offers.application.shared.CommandHandler;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class RejectOfferCommandHandler implements CommandHandler<RejectOfferCommand> {

    private final OfferRepository offerRepository;

    @Override
    public List<? extends DomainEvent> execute(final RejectOfferCommand command) {
        final Offer offer = offerRepository.findOrFail(command.getOfferId());
        offer.reject(command.getReason());
        return offerRepository.save(offer).pickDomainEvents();
    }
}
