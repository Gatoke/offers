package com.github.gatoke.offers.domain.user.vo;


import com.github.gatoke.offers.domain.user.exception.InvalidUserIdException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class UserId {

    private long id;

    public static UserId of(final long id) {
        if (id <= 0) {
            throw new InvalidUserIdException(format("User id: %d cannot be equal or less than 0!", id));
        }
        return new UserId(id);
    }

    public long getValue() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}
