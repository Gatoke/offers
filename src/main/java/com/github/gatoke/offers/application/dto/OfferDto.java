package com.github.gatoke.offers.application.dto;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class OfferDto {

    private final UUID id;
    private final long userId;
    private final String title;
    private final String content;
    private final OfferStatus status;
}
