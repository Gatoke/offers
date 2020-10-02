package com.github.gatoke.offers.domain.user.event;

import com.github.gatoke.offers.domain.shared.Event;
import com.github.gatoke.offers.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserRegisteredEvent extends Event<UserRegisteredEvent.Payload> {

    private final Payload payload;

    public UserRegisteredEvent(final User user) {
        this.payload = new Payload(user);
    }

    @Getter
    @RequiredArgsConstructor
    public static class Payload {

        private final User user;
    }
}
