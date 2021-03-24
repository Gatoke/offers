package com.github.gatoke.offers.application.offer;

import com.github.gatoke.offers.application.offer.command.CreateOfferCommand;
import com.github.gatoke.offers.application.shared.CommandHandler;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.user.UserRepository;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class CreateOfferCommandHandler implements CommandHandler<CreateOfferCommand> {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    @Override
    public List<? extends DomainEvent> execute(final CreateOfferCommand command) {
        if (userRepository.doesNotExist(command.getUserId())) {
            throw new UserNotFoundException(command.getUserId());
        }
        final Offer offer = offerRepository.save(
                Offer.create(
                        command.getOfferId(),
                        command.getUserId(),
                        command.getOfferType(),
                        command.getTitle(),
                        command.getContent(),
                        command.getPrice())
        );
        return offer.pickDomainEvents();
    }
}
