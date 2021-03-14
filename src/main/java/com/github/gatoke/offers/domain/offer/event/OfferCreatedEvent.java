package com.github.gatoke.offers.domain.offer.event;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.github.gatoke.offers.domain.shared.EventType.OFFER_CREATED;

@Getter
@NoArgsConstructor
public class OfferCreatedEvent implements DomainEvent {

    private final EventType type = OFFER_CREATED;

    private UUID offerId;
    private long userId;
    private String title;
    private String content;
    private OfferStatus status;
    private OffsetDateTime createdAt;

    public OfferCreatedEvent(final Offer offer) {
        this.offerId = offer.getId().getValue();
        this.userId = offer.getUserId().getValue();
        this.title = offer.getTitle();
        this.content = offer.getContent();
        this.status = offer.getStatus();
        this.createdAt = offer.getCreatedAt().getValue();
    }
}
