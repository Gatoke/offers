package com.github.gatoke.offers.application.shared;

import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CommandsRegistry {

    private final Map<Class<? extends Command>, CommandHandler<Command>> declaredHandlers = new HashMap<>();

    CommandsRegistry(final Set<CommandHandler<? extends Command>> commandHandlers) {
        for (final CommandHandler<? extends Command> handler : commandHandlers) {
            for (final Type genericInterface : handler.getClass().getGenericInterfaces()) {
                final Type[] actualTypeArguments = ((ParameterizedType) genericInterface).getActualTypeArguments();
                for (final Type actualTypeArgument : actualTypeArguments) {
                    if (Command.class.isAssignableFrom((Class<?>) actualTypeArgument)) {
                        declaredHandlers.put((Class<? extends Command>) actualTypeArgument, (CommandHandler<Command>) handler);
                    }
                }
            }
        }
    }

    public CommandHandler<Command> get(final Class<? extends Command> commandType) {
        return declaredHandlers.get(commandType);
    }
}
