package com.github.gatoke.offers.domain.offer;

import com.github.gatoke.offers.domain.offer.event.*;
import com.github.gatoke.offers.domain.offer.exception.InvalidOfferStatusStateException;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import com.github.gatoke.offers.domain.shared.Aggregate;
import com.github.gatoke.offers.domain.shared.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.github.gatoke.offers.domain.offer.vo.OfferStatus.*;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class Offer extends Aggregate {

    private UUID id;
    private long userId;
    private String title;
    private String content;
    private OfferStatus status;
    private Time createdAt;

    private Offer(final long userId,
                  final String title,
                  final String content) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.status = PENDING;
        this.createdAt = Time.now();
        registerEvent(new OfferCreatedEvent(this));
    }

    public static Offer create(final long userId, final String title, final String content) {
        return new Offer(userId, title, content);
    }

    public void accept() {
        if (PENDING != this.status) {
            throw new InvalidOfferStatusStateException(this.id, this.status);
        }
        this.status = ACCEPTED;
        registerEvent(new OfferAcceptedEvent(this.id, this.status));
    }

    public void reject(final String reason) {
        if (PENDING != this.status) {
            throw new InvalidOfferStatusStateException(this.id, this.status);
        }
        this.status = REJECTED;
        registerEvent(new OfferRejectedEvent(this.id, this.status, reason));
    }

    public void publish() {
        if (REJECTED == this.status) {
            throw new InvalidOfferStatusStateException(this.id, this.status);
        }
        this.status = OfferStatus.PUBLISHED;
        registerEvent(new OfferPublishedEvent(this.id, this.status));
    }

    public void finish() {
        this.status = FINISHED;
        registerEvent(new OfferFinishedEvent(this.id, this.status));
    }

    public void delete() {
        this.status = DELETED;
        registerEvent(new OfferDeletedEvent(this.id, this.status));
    }

    public void expire() {
        if (PUBLISHED != this.status) {
            throw new InvalidOfferStatusStateException(this.id, this.status);
        }
        this.status = EXPIRED;
        registerEvent(new OfferExpiredEvent(this.id, this.status));
    }
}
