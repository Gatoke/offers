package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.application.UserFacade;
import com.github.gatoke.offers.domain.user.exception.InvalidEmailException;
import com.github.gatoke.offers.domain.user.exception.InvalidNameException;
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.github.gatoke.offers.port.adapter.EntityCreatedResponseFactory.created;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserCommandEndpoint {

    private final UserFacade userFacade;

    @PostMapping
    ResponseEntity create(@RequestBody final CreateUserRequest request) {
        final long id = userFacade.create(request.userId, request.firstName, request.lastName, request.email);
        return created(id);
    }

    @ExceptionHandler
    ResponseEntity handleInvalidNameException(final InvalidNameException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleInvalidEmailException(final InvalidEmailException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUserAlreadyExistsException(final UserAlreadyExistsException ex) {
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
    }
}
