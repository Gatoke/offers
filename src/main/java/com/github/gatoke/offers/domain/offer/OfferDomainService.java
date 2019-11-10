package com.github.gatoke.offers.domain.offer;

import com.github.gatoke.offers.domain.shared.EventPublisher;
import com.github.gatoke.offers.domain.user.UserRepository;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static java.time.OffsetDateTime.now;

@RequiredArgsConstructor
public class OfferDomainService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    private static final Long OFFER_EXPIRATION_PERIOD = 7L;

    public UUID createOffer(final long userId, final String title, final String content) {
        if (userRepository.doesNotExist(userId)) {
            throw new UserNotFoundException(userId);
        }

        final Offer offer = Offer.create(userId, title, content);
        offerRepository.save(offer);
        publishEvents(offer);
        return offer.getId();
    }

    public void acceptOffer(final UUID offerId) {
        final Offer offer = offerRepository.findOrFail(offerId);

        offer.accept();
        publishEvents(offer);
        offerRepository.save(offer);
    }

    public void rejectOffer(final UUID offerId, final String reason) {
        final Offer offer = offerRepository.findOrFail(offerId);

        offer.reject(reason);
        publishEvents(offer);
        offerRepository.save(offer);
    }

    public void publishOffer(final UUID offerId) {
        final Offer offer = offerRepository.findOrFail(offerId);

        offer.publish();
        publishEvents(offer);
        offerRepository.save(offer);
    }

    public void finishOffer(final UUID offerId) {
        final Offer offer = offerRepository.findOrFail(offerId);

        offer.finish();
        publishEvents(offer);
        offerRepository.save(offer);
    }

    public void deleteOffer(final UUID offerId) {
        final Offer offer = offerRepository.findOrFail(offerId);

        offer.delete();
        publishEvents(offer);
        offerRepository.save(offer);
    }

    public void expireOutdatedOffers() {
        final List<Offer> outdatedOffers = offerRepository.findOffersCreatedBefore(
                now().minusDays(OFFER_EXPIRATION_PERIOD)
        );

        outdatedOffers.forEach(offer -> {
            offer.expire();
            publishEvents(offer);
            offerRepository.save(offer);
        });
    }

    private void publishEvents(final Offer offer) {
        offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
    }
}
