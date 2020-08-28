package com.github.gatoke.offers.domain.user;

import com.github.gatoke.offers.domain.shared.Aggregate;
import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.event.UserCreatedEvent;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class User extends Aggregate {

    private long id;
    private Name name;
    private Email email;
    private Time createdAt;

    private User(final long id, final Name name, final Email email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = Time.now();
        registerEvent(new UserCreatedEvent(this));
    }

    public static User create(final long id, final Name name, final Email email) {
        return new User(id, name, email);
    }
}
