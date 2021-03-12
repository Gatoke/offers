package com.github.gatoke.offers.application.user.command;

import com.github.gatoke.offers.application.shared.Command;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import lombok.Getter;

@Getter
public class RegisterUserCommand implements Command {

    private final long userId;
    private final Name name;
    private final Email email;

    public RegisterUserCommand(final long userId, final String firstName, final String lastName, final String email) {
        this.userId = userId;
        this.name = Name.of(firstName, lastName);
        this.email = Email.of(email);
    }
}
