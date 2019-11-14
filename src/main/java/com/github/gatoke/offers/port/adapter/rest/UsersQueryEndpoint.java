package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModel;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UsersQueryEndpoint {

    private final UserReadModelRepository userReadModelRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserReadModel> get(@PathVariable final String id) {
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(id);
        return ResponseEntity.ok(userReadModel);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUserNotFoundException(final UserNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }
}
