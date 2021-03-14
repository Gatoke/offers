package com.github.gatoke.offers.domain.user;

import com.github.gatoke.offers.domain.shared.Aggregate;
import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import com.github.gatoke.offers.domain.user.vo.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class User extends Aggregate {

    private UserId id;
    private Name name;
    private Email email;
    private Time registeredAt;

    private User(final UserId id, final Name name, final Email email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registeredAt = Time.now();
        registerEvent(new UserRegisteredEvent(this));
    }

    public static User register(final UserId id, final Name name, final Email email) {
        return new User(id, name, email);
    }
}
