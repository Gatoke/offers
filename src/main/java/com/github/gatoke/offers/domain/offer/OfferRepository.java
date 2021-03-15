package com.github.gatoke.offers.domain.offer;

import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.shared.Time;

import java.util.List;

public interface OfferRepository {

    Offer save(Offer offer);

    Offer findOrFail(OfferId offerId);

    List<Offer> findPublishedOffersCreatedBefore(Time time);
}
