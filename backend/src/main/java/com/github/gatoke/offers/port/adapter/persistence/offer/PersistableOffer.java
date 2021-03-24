package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.offer.vo.OfferType;
import com.github.gatoke.offers.domain.shared.Currency;
import com.github.gatoke.offers.domain.shared.Money;
import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.vo.UserId;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal price;

    private OffsetDateTime createdAt;

    static PersistableOffer from(final Offer offer) {
        final PersistableOffer persistableOffer = new PersistableOffer();
        persistableOffer.id = offer.getOfferId().getValue();
        persistableOffer.userId = offer.getUserId().getValue();
        persistableOffer.offerType = offer.getOfferType();
        persistableOffer.title = offer.getTitle();
        persistableOffer.content = offer.getContent();
        persistableOffer.status = offer.getStatus();
        persistableOffer.currency = offer.getPrice().getCurrency();
        persistableOffer.price = offer.getPrice().getAmount();
        persistableOffer.createdAt = offer.getCreatedAt().getValue();
        return persistableOffer;
    }

    Offer toDomainObject() {
        return Offer.builder()
                .offerId(OfferId.from(this.id))
                .userId(UserId.from(this.userId))
                .offerType(this.offerType)
                .title(this.title)
                .content(this.content)
                .status(this.status)
                .price(Money.from(this.currency, this.price))
                .createdAt(Time.from(this.createdAt))
                .build();
    }
}
