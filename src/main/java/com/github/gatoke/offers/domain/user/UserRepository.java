package com.github.gatoke.offers.domain.user;

public interface UserRepository {

    void save(User user);
    boolean doesNotExist(long userId);
    boolean exists(long userId);
}
