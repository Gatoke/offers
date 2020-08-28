package com.github.gatoke.offers.port.adapter.persistence.user;

import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.UserRepository;
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
    public boolean doesNotExist(final long userId) {
        return !repository.existsById(userId);
    }

    @Override
    public boolean exists(final long userId) {
        return repository.existsById(userId);
    }
}
