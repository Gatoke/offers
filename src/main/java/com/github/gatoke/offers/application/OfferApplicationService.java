package com.github.gatoke.offers.application;

import com.github.gatoke.offers.application.command.AcceptOfferCommand;
import com.github.gatoke.offers.application.command.CreateOfferCommand;
import com.github.gatoke.offers.application.command.DeleteOfferCommand;
import com.github.gatoke.offers.application.command.RejectOfferCommand;
import com.github.gatoke.offers.application.dto.OfferDto;
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

@Service
@Transactional
@RequiredArgsConstructor
public class OfferApplicationService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public OfferDto createOffer(final CreateOfferCommand createOfferCommand) {
        if (userRepository.doesNotExist(createOfferCommand.getUserId())) {
            throw new UserNotFoundException(createOfferCommand.getUserId());
        }
        final Offer offer = offerRepository.save(
                Offer.create(createOfferCommand.getUserId(), createOfferCommand.getTitle(), createOfferCommand.getContent())
        );
        offer.pickDomainEvents().forEach(eventPublisher::publish);

        return OfferDto.of(offer);
    }

    public void accept(final AcceptOfferCommand acceptOfferCommand) {
        final Offer offer = offerRepository.findOrFail(acceptOfferCommand.getOfferId());

        offer.accept();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publish);
    }

    public void reject(final RejectOfferCommand rejectOfferCommand) {
        final Offer offer = offerRepository.findOrFail(rejectOfferCommand.getOfferId());

        offer.reject(rejectOfferCommand.getReason());
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publish);
    }

    public void delete(final DeleteOfferCommand deleteOfferCommand) {
        final Offer offer = offerRepository.findOrFail(deleteOfferCommand.getOfferId());

        offer.delete();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publish);
    }

    public void publishOn(final OfferAcceptedEvent event) {
        final Offer offer = offerRepository.findOrFail(event.getOfferId());

        offer.publish();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publish);
    }

    public void finishOn(final OfferFinishedEvent event) {
        final Offer offer = offerRepository.findOrFail(event.getOfferId());

        offer.finish();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publish);
    }

    public void expire(final Offer offer) {
        offer.expire();
        offerRepository.save(offer);
        offer.pickDomainEvents().forEach(eventPublisher::publish);
    }
}
