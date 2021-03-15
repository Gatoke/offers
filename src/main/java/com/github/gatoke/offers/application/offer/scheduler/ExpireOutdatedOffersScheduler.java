package com.github.gatoke.offers.application.offer.scheduler;

import com.github.gatoke.offers.application.offer.command.ExpireOfferCommand;
import com.github.gatoke.offers.application.shared.CommandBus;
import com.github.gatoke.offers.domain.offer.FindOutdatedOffersService;
import com.github.gatoke.offers.domain.offer.Offer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class ExpireOutdatedOffersScheduler {

    private final FindOutdatedOffersService findOutdatedOffersService;
    private final CommandBus commandBus;

    @Scheduled(fixedDelayString = "PT5M")
    @SchedulerLock(name = "expireOutdatedOffersScheduler", lockAtMostFor = "60M")
    void expireOutdatedOffers() {
        final List<Offer> offersToExpire = findOutdatedOffersService.find();
        offersToExpire.forEach(offer -> {
            try {
                final ExpireOfferCommand command = new ExpireOfferCommand(offer.getId());
                commandBus.execute(command);
            } catch (final Exception ex) {
                log.warn("Expiring offer with id: {} failed. Cause: {}", offer.getId(), ex.getMessage());
            }
        });
    }
}
