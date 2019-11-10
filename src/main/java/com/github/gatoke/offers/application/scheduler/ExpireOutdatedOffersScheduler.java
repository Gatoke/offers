package com.github.gatoke.offers.application.scheduler;

import com.github.gatoke.offers.domain.offer.OfferDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
class ExpireOutdatedOffersScheduler {

    private final OfferDomainService offerDomainService;

    @Transactional
    @Scheduled(fixedDelayString = "PT15M")
    void expireOutdatedOffers() {
        try {
            offerDomainService.expireOutdatedOffers();
        } catch (final Exception ex) {
            log.error("Could not expire outdated offers.", ex);
        }
    }
}
