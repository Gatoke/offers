package com.github.gatoke.offers.application.shared;

import com.github.gatoke.offers.domain.shared.DomainEvent;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static java.lang.System.currentTimeMillis;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "com.github.gatoke.offers.commandbus")
public class CommandBus {

    private final EventPublisher eventPublisher;
    private final CommandsRegistry commandsRegistry;
    private final TransactionTemplate transactionTemplate;

    public void execute(final Command command) {
        final CommandHandler<Command> commandHandler = commandsRegistry.get(command.getClass());

        final long start = currentTimeMillis();
        try {
            transactionTemplate.executeWithoutResult(status -> {
                final List<? extends DomainEvent> resultEvents = commandHandler.execute(command);
                resultEvents.forEach(eventPublisher::publish);
            });
        } finally {
            final long end = currentTimeMillis();
            final long timeElapsed = end - start;
            log.debug("Command handler {} finished in {}ms", commandHandler.getClass().getSimpleName(), timeElapsed);
        }
    }
}
