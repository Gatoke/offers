package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.application.UserApplicationService;
import com.github.gatoke.offers.application.command.CreateUserCommand;
import com.github.gatoke.offers.application.dto.UserDto;
import com.github.gatoke.offers.domain.user.exception.InvalidEmailException;
import com.github.gatoke.offers.domain.user.exception.InvalidNameException;
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UsersCommandEndpoint {

    private final UserApplicationService userApplicationService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid final CreateUserRequest request) {
        final UserDto user = userApplicationService.createUser(request.toCommand());
        return status(CREATED).body(user);
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidNameException(final InvalidNameException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidEmailException(final InvalidEmailException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserAlreadyExistsException(final UserAlreadyExistsException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @Data
    private static class CreateUserRequest {

        @NotNull
        private Long userId;

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @NotBlank
        private String email;

        CreateUserCommand toCommand() {
            return new CreateUserCommand(userId, firstName, lastName, email);
        }
    }
}
