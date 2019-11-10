package com.github.gatoke.offers.domain.user;

import com.github.gatoke.offers.domain.shared.EventPublisher;
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository repository;
    private final EventPublisher eventPublisher;

    public long createUser(final long userId, final Name name, final Email email) {
        if (repository.exists(userId)) {
            throw new UserAlreadyExistsException(userId);
        }

        final User user = User.create(userId, name, email);
        repository.save(user);

        user.pickDomainEvents().forEach(eventPublisher::publishEvent);
        return user.getId();
    }
}
