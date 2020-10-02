package com.github.gatoke.offers.port.adapter.persistence.user;

import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserReadModelRepository {

    private final UserReadModelJpaRepository repository;

    public void createOn(final UserRegisteredEvent userRegisteredEvent) {
        final UserReadModel userReadModel = UserReadModel.of(userRegisteredEvent.getPayload().getUser());
        repository.save(userReadModel);
    }

    public void save(final UserReadModel userReadModel) {
        repository.save(userReadModel);
    }

    public UserReadModel findOrThrow(final String userId) {
        try {
            final Long id = Long.valueOf(userId);
            final Optional<UserReadModel> userReadModelOptional = repository.findById(id);
            return userReadModelOptional.orElseThrow(() -> new UserNotFoundException(id));
        } catch (final NumberFormatException ex) {
            throw new UserNotFoundException(userId);
        }
    }
}
