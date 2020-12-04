package com.github.gatoke.offers.domain.user.vo;

import com.github.gatoke.offers.domain.user.exception.InvalidNameException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class Name {

    private static final int MINIMUM_LENGTH = 3;

    private String firstName;
    private String lastName;

    public static Name of(final String firstName, final String lastName) {
        if (firstName == null || withoutWhitespaces(firstName).length() < MINIMUM_LENGTH) {
            throw new InvalidNameException(format("First name: %s should have minimum length of: %d non-whitespace characters.",
                    firstName, MINIMUM_LENGTH));
        }

        if (lastName == null || withoutWhitespaces(lastName).length() < MINIMUM_LENGTH) {
            throw new InvalidNameException(format("Last name: %s should have minimum length of: %d non-whitespace characters.",
                    lastName, MINIMUM_LENGTH));
        }

        return new Name(withoutWhitespaces(firstName), withoutWhitespaces(lastName));
    }

    private static String withoutWhitespaces(final String input) {
        return input.replace(" ", "");
    }
}
