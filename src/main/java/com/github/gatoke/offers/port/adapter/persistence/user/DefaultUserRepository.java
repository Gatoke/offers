package com.github.gatoke.offers.port.adapter.persistence.user;

import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.UserRepository;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultUserRepository implements UserRepository {

    private final UserJpaRepository repository;

    @Override
    public User findOrThrow(final long id) {
        final Optional<PersistableUser> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get().toDomainObject();
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public void save(final User user) {
        final PersistableUser persistableUser = PersistableUser.of(user);
        repository.save(persistableUser);
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
