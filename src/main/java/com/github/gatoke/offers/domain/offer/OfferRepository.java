package com.github.gatoke.offers.domain.offer;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface OfferRepository {

    Offer save(Offer offer);

    Offer findOrFail(UUID offerId);

    List<Offer> findPublishedOffersCreatedBefore(OffsetDateTime dateTime);
}
