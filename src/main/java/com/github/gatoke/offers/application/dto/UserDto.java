package com.github.gatoke.offers.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
}
