package com.github.gatoke.offers.port.adapter.rest.users;

import com.github.gatoke.offers.application.UserApplicationService;
import com.github.gatoke.offers.application.command.RegisterUserCommand;
import com.github.gatoke.offers.application.dto.UserDto;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UsersCommandEndpoint {

    private final UserApplicationService userApplicationService;

    @PostMapping
    ResponseEntity<UserDto> registerUser(@RequestBody @Valid final RegisterUserRequest request) {
        final UserDto user = userApplicationService.registerUser(request.toCommand());
        return status(CREATED).body(user);
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
