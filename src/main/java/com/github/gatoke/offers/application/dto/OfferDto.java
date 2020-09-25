package com.github.gatoke.offers.application.dto;

import com.github.gatoke.offers.domain.offer.Offer;
import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class OfferDto {

    private final UUID id;
    private final long userId;
    private final String title;
    private final String content;
    private final OfferStatus status;

    public static OfferDto of(final Offer offer) {
        return new OfferDto(
                offer.getId(),
                offer.getUserId(),
                offer.getTitle(),
                offer.getContent(),
                offer.getStatus()
        );
    }
}
