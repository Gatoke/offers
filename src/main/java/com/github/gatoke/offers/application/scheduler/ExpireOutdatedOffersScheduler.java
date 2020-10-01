package com.github.gatoke.offers.application.scheduler;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.domain.offer.FindOutdatedOffersService;
import com.github.gatoke.offers.domain.offer.Offer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class ExpireOutdatedOffersScheduler {

    private final FindOutdatedOffersService findOutdatedOffersService;
    private final OfferApplicationService offerApplicationService;

    @Scheduled(fixedDelayString = "PT15M")
    void expireOutdatedOffers() {
        final List<Offer> offersToExpire = findOutdatedOffersService.find();
        offersToExpire.forEach(offer -> {
            try {
                offerApplicationService.expire(offer);
            } catch (final Exception ex) {
                log.warn("Expiring offer with id: {} failed. Cause: {}", offer.getId(), ex.getMessage());
            }
        });
    }
}
