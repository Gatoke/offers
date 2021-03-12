package com.github.gatoke.offers.port.adapter.rest.users;

import com.github.gatoke.offers.application.shared.CommandBus;
import com.github.gatoke.offers.application.user.command.RegisterUserCommand;
import com.github.gatoke.offers.port.adapter.rest.ResourceCreatedResponseBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UsersCommandEndpoint {

    private final CommandBus commandBus;

    @PostMapping
    ResponseEntity<?> registerUser(@RequestBody @Valid final RegisterUserRequest request) {
        commandBus.execute(request.toCommand());
        return ResourceCreatedResponseBuilder.fromId(request.userId);
    }

    @Data
    private static class RegisterUserRequest {

        @NotNull
        private Long userId;

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @NotBlank
        private String email;

        RegisterUserCommand toCommand() {
            return new RegisterUserCommand(userId, firstName, lastName, email);
        }
    }
}
