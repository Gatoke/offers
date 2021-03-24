package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.event.OfferCreatedEvent;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Repository
@RequiredArgsConstructor
public class OfferReadModelRepository {

    private final OfferReadModelJpaRepository repository;

    public void create(final OfferCreatedEvent offerCreatedEvent) {
        final OfferReadModel offerReadModel = OfferReadModel.from(offerCreatedEvent);
        repository.save(offerReadModel);
    }

    public OfferReadModel findOrThrow(final UUID offerId) {
        final Optional<OfferReadModel> offerReadModelOptional = repository.findById(offerId);
        return offerReadModelOptional.orElseThrow(() -> new OfferNotFoundException(offerId));
    }

    public void save(final OfferReadModel offerReadModel) {
        repository.save(offerReadModel);
    }

    public Page<OfferReadModel> findAllWithFilter(final Long userId, final String offerStatus, final Pageable pageable) {
        try {
            return repository.findAllWithFilter(
                    userId,
                    isBlank(offerStatus) ? null : OfferStatus.from(offerStatus),
                    pageable
            );
        } catch (final NumberFormatException ex) {
            throw new UserNotFoundException(userId);
        }
    }
}
