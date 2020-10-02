package com.github.gatoke.offers.application;

import com.github.gatoke.offers.application.command.RegisterUserCommand;
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

    public UserDto registerUser(final RegisterUserCommand registerUserCommand) {
        if (repository.exists(registerUserCommand.getUserId())) {
            throw new UserAlreadyExistsException(registerUserCommand.getUserId());
        }

        final User user = repository.save(
                User.register(registerUserCommand.getUserId(), registerUserCommand.getName(), registerUserCommand.getEmail())
        );

        user.pickDomainEvents().forEach(eventPublisher::publishEvent);

        return UserDto.of(user);
    }
}
