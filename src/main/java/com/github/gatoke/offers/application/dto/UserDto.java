package com.github.gatoke.offers.application.dto;

import com.github.gatoke.offers.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class UserDto {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public static UserDto of(final User user) {
        return new UserDto(
                user.getId(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getEmail().getValue()
        );
    }
}
