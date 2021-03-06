package com.github.gatoke.offers.port.adapter.persistence.user;

import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserReadModelRepository {

    private final UserReadModelJpaRepository repository;

    public void create(final UserRegisteredEvent userRegisteredEvent) {
        final UserReadModel userReadModel = UserReadModel.from(userRegisteredEvent);
        repository.save(userReadModel);
    }

    public void save(final UserReadModel userReadModel) {
        repository.save(userReadModel);
    }

    public UserReadModel findOrThrow(final long userId) {
        try {
            final Optional<UserReadModel> userReadModelOptional = repository.findById(userId);
            return userReadModelOptional.orElseThrow(() -> new UserNotFoundException(userId));
        } catch (final NumberFormatException ex) {
            throw new UserNotFoundException(userId);
        }
    }

    public Page<UserReadModel> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }
}
