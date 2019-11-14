package com.github.gatoke.offers.application;

import com.github.gatoke.offers.application.dto.OfferDto;
import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.OfferDomainService;
import com.github.gatoke.offers.domain.offer.event.OfferAcceptedEvent;
import com.github.gatoke.offers.domain.offer.event.OfferFinishedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OfferApplicationService {

    private final OfferDomainService offerDomainService;

    public OfferDto create(final long userId, final String title, final String content) {
        final Offer offer = offerDomainService.createOffer(userId, title, content);
        return new OfferDto(
                offer.getId(),
                offer.getUserId(),
                offer.getTitle(),
                offer.getContent(),
                offer.getStatus()
        );
    }

    public void accept(final String offerId) {
        final UUID id = UUID.fromString(offerId);
        offerDomainService.acceptOffer(id);
    }

    public void reject(final String offerId, final String reason) {
        final UUID id = UUID.fromString(offerId);
        offerDomainService.rejectOffer(id, reason);
    }

    public void delete(final String offerId) {
        final UUID id = UUID.fromString(offerId);
        offerDomainService.deleteOffer(id);
    }

    public void publishOn(final OfferAcceptedEvent event) {
        offerDomainService.publishOffer(event.getPayload().getOfferId());
    }

    public void finishOn(final OfferFinishedEvent event) {
        offerDomainService.finishOffer(event.getPayload().getOfferId());
    }
}
