package com.github.gatoke.offers.application.shared;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandBus {

    private final EventPublisher eventPublisher;
    private final CommandsRegistry commandsRegistry;

    @Transactional
    public <T extends Command> void execute(final Command command) {
        final CommandHandler<Command> commandHandler = commandsRegistry.get(command.getClass());

        final List<? extends DomainEvent> resultEvents = commandHandler.execute(command);
        resultEvents.forEach(eventPublisher::publish);
    }
}
