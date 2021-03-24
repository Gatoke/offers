package com.github.gatoke.offers.application.user;

import com.github.gatoke.offers.application.shared.CommandHandler;
import com.github.gatoke.offers.application.user.command.RegisterUserCommand;
import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.UserRepository;
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand> {

    private final UserRepository repository;

    @Override
    public List<? extends DomainEvent> execute(final RegisterUserCommand command) {
        if (repository.exists(command.getUserId())) {
            throw new UserAlreadyExistsException(command.getUserId());
        }
        final User user = User.register(
                command.getUserId(),
                command.getName(),
                command.getEmail()
        );
        return repository.save(user).pickDomainEvents();
    }
}
