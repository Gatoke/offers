package com.github.gatoke.offers.domain.user.event;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventType;
import com.github.gatoke.offers.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

import static com.github.gatoke.offers.domain.shared.EventType.USER_REGISTERED;

@Getter
@NoArgsConstructor
public class UserRegisteredEvent implements DomainEvent {

    private final EventType eventType = USER_REGISTERED;

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private OffsetDateTime registeredAt;

    public UserRegisteredEvent(final User user) {
        this.id = user.getId().getValue();
        this.firstName = user.getName().getFirstName();
        this.lastName = user.getName().getLastName();
        this.email = user.getEmail().getValue();
        this.registeredAt = user.getRegisteredAt().getValue();
    }
}
