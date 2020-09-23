package com.github.gatoke.offers.application;

import com.github.gatoke.offers.application.command.AcceptOfferCommand;
import com.github.gatoke.offers.application.command.CreateOfferCommand;
import com.github.gatoke.offers.application.command.DeleteOfferCommand;
import com.github.gatoke.offers.application.command.RejectOfferCommand;
import com.github.gatoke.offers.application.dto.OfferDto;
import com.github.gatoke.offers.domain.offer.FindOutdatedOffersService;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.offer.event.OfferAcceptedEvent;
import com.github.gatoke.offers.domain.offer.event.OfferFinishedEvent;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import com.github.gatoke.offers.domain.user.UserRepository;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OfferApplicationService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;
    private final FindOutdatedOffersService findOutdatedOffersService;

    public OfferDto createOffer(final CreateOfferCommand createOfferCommand) {
        if (userRepository.doesNotExist(createOfferCommand.getUserId())) {
            throw new UserNotFoundException(createOfferCommand.getUserId());
        }
        final Offer offer = offerRepository.save(
                Offer.create(createOfferCommand.getUserId(), createOfferCommand.getTitle(), createOfferCommand.getContent())
        );
        offer.pickDomainEvents().forEach(eventPublisher::publishEvent);

        return new OfferDto(
                offer.getId(),
                offer.getUserId(),
                offer.getTitle(),
                offer.getContent(),
                offer.getStatus()
        );
    }

    public void accept(final AcceptOfferCommand acceptOfferCommand) {
        final Offer offer = offerRepository.findOrFail(acceptOfferCommand.getOfferId());

        offer.accept();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
    }

    public void reject(final RejectOfferCommand rejectOfferCommand) {
        final Offer offer = offerRepository.findOrFail(rejectOfferCommand.getOfferId());

        offer.reject(rejectOfferCommand.getReason());
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
    }

    public void delete(final DeleteOfferCommand deleteOfferCommand) {
        final Offer offer = offerRepository.findOrFail(deleteOfferCommand.getOfferId());

        offer.delete();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
    }

    public void publishOn(final OfferAcceptedEvent event) {
        final Offer offer = offerRepository.findOrFail(event.getPayload().getOfferId());

        offer.publish();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
    }

    public void finishOn(final OfferFinishedEvent event) {
        final Offer offer = offerRepository.findOrFail(event.getPayload().getOfferId());

        offer.finish();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
    }

    public void expireOutdatedOffers() {
        final List<Offer> outdatedOffers = findOutdatedOffersService.find();
        outdatedOffers.forEach(offer -> {
            offer.expire();
            offerRepository.save(offer);
            offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
        });
    }
}
