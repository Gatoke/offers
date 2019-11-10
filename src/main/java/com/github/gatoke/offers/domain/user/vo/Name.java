package com.github.gatoke.offers.domain.user.vo;

import com.github.gatoke.offers.domain.user.exception.InvalidNameException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public class Name {

    private static final int MINIMUM_LENGTH = 3;

    private final String firstName;
    private final String lastName;

    public static Name of(final String firstName, final String lastName) {
        if (firstName == null || withoutWhitespaces(firstName).length() < MINIMUM_LENGTH) {
            throw new InvalidNameException(format("First name: %s is invalid.", firstName));
        }

        if (lastName == null || withoutWhitespaces(lastName).length() < MINIMUM_LENGTH) {
            throw new InvalidNameException(format("Last name: %s is invalid.", lastName));
        }

        return new Name(withoutWhitespaces(firstName), withoutWhitespaces(lastName));
    }

    private static String withoutWhitespaces(final String input) {
        return input.replace(" ", "");
    }
}
