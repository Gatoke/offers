package com.github.gatoke.offers.application.shared;

import com.github.gatoke.offers.domain.shared.DomainEvent;

import java.util.List;

public interface CommandHandler<T extends Command> {

    List<? extends DomainEvent> execute(final T command);
}
