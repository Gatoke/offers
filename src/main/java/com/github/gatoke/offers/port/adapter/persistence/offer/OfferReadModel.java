package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.event.OfferCreatedEvent;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.offer.vo.OfferType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "offer_read_model")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OfferReadModel {

    @Id
    private UUID id;

    private long userId;

    @Enumerated(STRING)
    private OfferType offerType;

    private String title;

    private String content;

    @Enumerated(STRING)
    private OfferStatus status;

    private OffsetDateTime createdAt;

    private String rejectedReason;

    static OfferReadModel of(final OfferCreatedEvent offer) {
        final OfferReadModel offerReadModel = new OfferReadModel();
        offerReadModel.id = offer.getOfferId();
        offerReadModel.userId = offer.getUserId();
        offerReadModel.offerType = offer.getOfferType();
        offerReadModel.title = offer.getTitle();
        offerReadModel.content = offer.getContent();
        offerReadModel.status = offer.getStatus();
        offerReadModel.createdAt = offer.getCreatedAt();
        offerReadModel.rejectedReason = null;
        return offerReadModel;
    }
}
