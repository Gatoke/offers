package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_ACCEPTED;

@Getter
@NoArgsConstructor
public class OfferAcceptedEvent implements DomainEvent {

    private final EventType eventType = OFFER_ACCEPTED;

    private UUID offerId;
    private OfferStatus offerStatus;

    public OfferAcceptedEvent(final OfferId offerId, final OfferStatus offerStatus) {
        this.offerId = offerId.getValue();
        this.offerStatus = offerStatus;
    }
}
