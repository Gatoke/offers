package com.github.gatoke.offers.domain.user.event;

import com.github.gatoke.offers.domain.shared.Event;
import com.github.gatoke.offers.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreatedEvent extends Event {

    private final User user;
}
