package com.github.gatoke.offers.port.adapter.persistence.user;

import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.UserRepository;
import com.github.gatoke.offers.domain.user.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DefaultUserRepository implements UserRepository {

    private final UserJpaRepository repository;

    @Override
    public User save(final User user) {
        final PersistableUser persistableUser = PersistableUser.of(user);
        repository.save(persistableUser);
        return user;
    }

    @Override
    public boolean doesNotExist(final UserId userId) {
        return !repository.existsById(userId.getValue());
    }

    @Override
    public boolean exists(final UserId userId) {
        return repository.existsById(userId.getValue());
    }
}
