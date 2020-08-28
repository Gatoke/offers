package com.github.gatoke.offers.application.scheduler;

import com.github.gatoke.offers.domain.offer.FindOutdatedOffersService;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class ExpireOutdatedOffersScheduler {

    private final OfferRepository offerRepository;
    private final EventPublisher eventPublisher;
    private final FindOutdatedOffersService findOutdatedOffersService;

    @Transactional
    @Scheduled(fixedDelayString = "PT15M")
    void expireOutdatedOffers() {
        try {
            final List<Offer> outdatedOffers = findOutdatedOffersService.find();
            outdatedOffers.forEach(offer -> {
                offer.expire();
                offer.pickDomainEvents().forEach(eventPublisher::publishEvent);
                offerRepository.save(offer);
            });
        } catch (final Exception ex) {
            log.error("Could not expire outdated offers.", ex);
        }
    }
}
