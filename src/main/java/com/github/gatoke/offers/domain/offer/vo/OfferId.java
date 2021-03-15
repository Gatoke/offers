package com.github.gatoke.offers.domain.offer.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class OfferId {

    private UUID id;

    public static OfferId newId() {
        return new OfferId(UUID.randomUUID());
    }

    public static OfferId of(final UUID id) {
        return new OfferId(id);
    }

    public static OfferId of(final String id) {
        return new OfferId(UUID.fromString(id));
    }

    public UUID getValue() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
