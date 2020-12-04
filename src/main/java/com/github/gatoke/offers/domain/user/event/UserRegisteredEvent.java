package com.github.gatoke.offers.domain.user.event;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.github.gatoke.offers.domain.shared.EventType.USER_REGISTERED;


@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class UserRegisteredEvent implements DomainEvent<User> {

    private final EventType type = USER_REGISTERED;

    private UUID id;
    private Time occurredOn;
    private User payload;

    public UserRegisteredEvent(final User user) {
        this.id = UUID.randomUUID();
        this.occurredOn = Time.now();
        this.payload = user;
    }
}
