package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_REJECTED;

@Getter
@NoArgsConstructor
public class OfferRejectedEvent implements DomainEvent {

    private final EventType type = OFFER_REJECTED;

    private UUID offerId;
    private OfferStatus offerStatus;
    private String reason;

    public OfferRejectedEvent(final UUID offerId, final OfferStatus offerStatus, final String reason) {
        this.offerId = offerId;
        this.offerStatus = offerStatus;
        this.reason = reason;
    }
}
