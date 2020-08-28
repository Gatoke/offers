package com.github.gatoke.offers.domain.user;

public interface UserRepository {

    User save(User user);

    boolean doesNotExist(long userId);

    boolean exists(long userId);
}
