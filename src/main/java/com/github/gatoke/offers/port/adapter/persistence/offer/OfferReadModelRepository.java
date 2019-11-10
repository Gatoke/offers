package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OfferReadModelRepository {

    private final OfferReadModelJpaRepository repository;

    public void create(final Offer offer) {
        final OfferReadModel offerReadModel = OfferReadModel.of(offer);
        repository.save(offerReadModel);
    }

    public OfferReadModel findOrThrow(final UUID offerId) {
        final Optional<OfferReadModel> offerReadModelOptional = repository.findById(offerId);
        if (offerReadModelOptional.isPresent()) {
            return offerReadModelOptional.get();
        }
        throw new OfferNotFoundException(offerId);
    }

    public void save(final OfferReadModel offerReadModel) {
        repository.save(offerReadModel);
    }

    public List<OfferReadModel> findAllWithFilter(final String userId, final String offerStatus) {
        try {
            return repository.findAllWithFilter(
                    userId == null || userId.isBlank() ? null : Long.valueOf(userId),
                    offerStatus == null || offerStatus.isBlank() ? null : OfferStatus.of(offerStatus)
            );
        } catch (final NumberFormatException ex) {
            throw new UserNotFoundException(userId);
        }
    }
}
