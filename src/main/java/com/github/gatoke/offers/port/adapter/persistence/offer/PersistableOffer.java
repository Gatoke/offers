package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.offer.vo.OfferType;
import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.vo.UserId;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "offer")
class PersistableOffer {

    @Id
    private UUID id;

    private long userId;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    private OffsetDateTime createdAt;

    static PersistableOffer of(final Offer offer) {
        final PersistableOffer persistableOffer = new PersistableOffer();
        persistableOffer.id = offer.getId().getValue();
        persistableOffer.userId = offer.getUserId().getValue();
        persistableOffer.offerType = offer.getOfferType();
        persistableOffer.title = offer.getTitle();
        persistableOffer.content = offer.getContent();
        persistableOffer.status = offer.getStatus();
        persistableOffer.createdAt = offer.getCreatedAt().getValue();
        return persistableOffer;
    }

    Offer toDomainObject() {
        return Offer.builder()
                .id(OfferId.of(this.id))
                .userId(UserId.of(this.userId))
                .offerType(this.offerType)
                .title(this.title)
                .content(this.content)
                .status(this.status)
                .createdAt(Time.of(this.createdAt))
                .build();
    }
}
