package com.github.gatoke.offers.application;

import com.github.gatoke.offers.application.command.CreateUserCommand;
import com.github.gatoke.offers.application.dto.UserDto;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.UserRepository;
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository repository;
    private final EventPublisher eventPublisher;

    public UserDto createUser(final CreateUserCommand createUserCommand) {
        if (repository.exists(createUserCommand.getUserId())) {
            throw new UserAlreadyExistsException(createUserCommand.getUserId());
        }

        final User user = repository.save(
                User.create(createUserCommand.getUserId(), createUserCommand.getName(), createUserCommand.getEmail())
        );

        user.pickDomainEvents().forEach(eventPublisher::publishEvent);

        return new UserDto(
                user.getId(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getEmail().getValue()
        );
    }
}
