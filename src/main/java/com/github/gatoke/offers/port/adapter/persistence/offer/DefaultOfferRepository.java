package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.shared.Time;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.gatoke.offers.domain.offer.vo.OfferStatus.PUBLISHED;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class DefaultOfferRepository implements OfferRepository {

    private final OfferJpaRepository repository;

    @Override
    public Offer save(final Offer offer) {
        final PersistableOffer persistableOffer = PersistableOffer.of(offer);
        repository.save(persistableOffer);
        return offer;
    }

    @Override
    public Offer findOrFail(final OfferId offerId) {
        final Optional<PersistableOffer> offerOptional = repository.findById(offerId.getValue());
        return offerOptional.map(PersistableOffer::toDomainObject)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

    @Override
    public List<Offer> findPublishedOffersCreatedBefore(final Time time) {
        final List<PersistableOffer> persistableOffers =
                repository.findAllByCreatedAtBeforeAndStatusEquals(time.getValue(), PUBLISHED);
        return persistableOffers.stream()
                .map(PersistableOffer::toDomainObject)
                .collect(toList());
    }
}
