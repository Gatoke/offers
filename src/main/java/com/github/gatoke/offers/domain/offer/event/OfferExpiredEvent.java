package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_EXPIRED;

@Getter
@NoArgsConstructor
public class OfferExpiredEvent implements DomainEvent {

    private final EventType type = OFFER_EXPIRED;

    private UUID offerId;
    private OfferStatus offerStatus;

    public OfferExpiredEvent(final UUID offerId, final OfferStatus offerStatus) {
        this.offerId = offerId;
        this.offerStatus = offerStatus;
    }
}
