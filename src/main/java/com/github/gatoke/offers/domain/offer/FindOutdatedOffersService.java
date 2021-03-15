package com.github.gatoke.offers.domain.offer;

import com.github.gatoke.offers.domain.shared.Time;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindOutdatedOffersService {

    private final OfferRepository offerRepository;

    private static final Long OFFER_EXPIRATION_PERIOD = 7L;

    public List<Offer> find() {

        return offerRepository.findPublishedOffersCreatedBefore(
                Time.daysAgo(OFFER_EXPIRATION_PERIOD)
        );
    }
}
