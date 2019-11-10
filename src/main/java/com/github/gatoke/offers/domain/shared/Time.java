package com.github.gatoke.offers.domain.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.OffsetDateTime;

import static java.time.Clock.systemUTC;
import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public class Time {

    private static final Clock CLOCK = systemUTC();

    private final OffsetDateTime value;

    public static Time now() {
        return new Time(OffsetDateTime.now(CLOCK));
    }

    public static Time of(final OffsetDateTime offsetDateTime) {
        return new Time(offsetDateTime);
    }
}
