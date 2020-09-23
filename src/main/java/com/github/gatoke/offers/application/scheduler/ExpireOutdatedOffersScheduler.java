package com.github.gatoke.offers.application.scheduler;

import com.github.gatoke.offers.application.OfferApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ExpireOutdatedOffersScheduler {

    private final OfferApplicationService offerApplicationService;

    @Scheduled(fixedDelayString = "PT15M")
    void expireOutdatedOffers() {
        offerApplicationService.expireOutdatedOffers();
    }
}
