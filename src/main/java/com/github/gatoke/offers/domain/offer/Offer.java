package com.github.gatoke.offers.domain.offer;

import com.github.gatoke.offers.domain.offer.event.*;
import com.github.gatoke.offers.domain.offer.exception.InvalidOfferStatusStateException;
import com.github.gatoke.offers.domain.offer.vo.OfferId;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.offer.vo.OfferType;
import com.github.gatoke.offers.domain.shared.Aggregate;
import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.vo.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.github.gatoke.offers.domain.offer.vo.OfferStatus.*;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class Offer extends Aggregate {

    private OfferId offerId;
    private UserId userId;
    private OfferType offerType;
    private String title;
    private String content;
    private OfferStatus status;
    private Time createdAt;

    private Offer(final OfferId offerId,
                  final UserId userId,
                  final OfferType offerType,
                  final String title,
                  final String content) {
        this.offerId = offerId;
        this.userId = userId;
        this.offerType = offerType;
        this.title = title;
        this.content = content;
        this.status = PENDING;
        this.createdAt = Time.now();
        registerEvent(new OfferCreatedEvent(this));
    }

    public static Offer create(final OfferId offerId,
                               final UserId userId,
                               final OfferType offerType,
                               final String title,
                               final String content) {
        return new Offer(offerId, userId, offerType, title, content);
    }

    public void accept() {
        if (PENDING != this.status) {
            throw new InvalidOfferStatusStateException(this.offerId, this.status);
        }
        this.status = ACCEPTED;
        registerEvent(new OfferAcceptedEvent(this.offerId, this.status));
    }

    public void reject(final String reason) {
        if (PENDING != this.status) {
            throw new InvalidOfferStatusStateException(this.offerId, this.status);
        }
        this.status = REJECTED;
        registerEvent(new OfferRejectedEvent(this.offerId, this.status, reason));
    }

    public void publish() {
        if (REJECTED == this.status) {
            throw new InvalidOfferStatusStateException(this.offerId, this.status);
        }
        this.status = OfferStatus.PUBLISHED;
        registerEvent(new OfferPublishedEvent(this.offerId, this.status));
    }

    public void finish() {
        this.status = FINISHED;
        registerEvent(new OfferFinishedEvent(this.offerId, this.status));
    }

    public void delete() {
        this.status = DELETED;
        registerEvent(new OfferDeletedEvent(this.offerId, this.status));
    }

    public void expire() {
        if (PUBLISHED != this.status) {
            throw new InvalidOfferStatusStateException(this.offerId, this.status);
        }
        this.status = EXPIRED;
        registerEvent(new OfferExpiredEvent(this.offerId, this.status));
    }
}
