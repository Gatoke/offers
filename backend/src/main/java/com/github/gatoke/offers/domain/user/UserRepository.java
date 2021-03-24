package com.github.gatoke.offers.domain.user;

import com.github.gatoke.offers.domain.user.vo.UserId;

public interface UserRepository {

    User save(User user);

    boolean doesNotExist(UserId userId);

    boolean exists(UserId userId);
}
