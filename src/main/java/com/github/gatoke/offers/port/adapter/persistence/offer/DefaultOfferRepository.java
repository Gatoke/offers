package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DefaultOfferRepository implements OfferRepository {

    private final OfferJpaRepository repository;

    @Override
    public void save(final Offer offer) {
        final PersistableOffer persistableOffer = PersistableOffer.of(offer);
        repository.save(persistableOffer);
    }

    @Override
    public Offer findOrFail(final UUID offerId) {
        final Optional<PersistableOffer> offerOptional = repository.findById(offerId);
        return offerOptional.map(PersistableOffer::toDomainObject)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

    @Override
    public List<Offer> findOffersCreatedBefore(final OffsetDateTime dateTime) {
        final List<PersistableOffer> persistableOffers = repository.findAllByCreatedAtBefore(dateTime);
        return persistableOffers.stream()
                .map(PersistableOffer::toDomainObject)
                .collect(Collectors.toList());
    }
}
