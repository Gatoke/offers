package com.github.gatoke.offers.domain.offer;

import lombok.AllArgsConstructor;

import java.util.List;

import static java.time.OffsetDateTime.now;

@AllArgsConstructor
public class FindOutdatedOffersService {

    private final OfferRepository offerRepository;

    private static final Long OFFER_EXPIRATION_PERIOD = 7L;

    public List<Offer> find() {
        return offerRepository.findOffersCreatedBefore(
                now().minusDays(OFFER_EXPIRATION_PERIOD)
        );
    }
}
