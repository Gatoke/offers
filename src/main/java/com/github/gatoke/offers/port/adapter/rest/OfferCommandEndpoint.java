package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.domain.offer.exception.InvalidOfferStatusStateException;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.github.gatoke.offers.port.adapter.EntityCreatedResponseFactory.created;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
class OfferCommandEndpoint {

    private final OfferApplicationService offerApplicationService;

    @PostMapping
    ResponseEntity create(@RequestBody final CreateOfferRequest request) {
        final UUID id = offerApplicationService.create(request.userId, request.title, request.content);
        return created(id);
    }

    @PostMapping("/accept/{id}")
    ResponseEntity accept(@PathVariable final String id) {
        offerApplicationService.accept(id);
        return ok().build();
    }

    @PostMapping("/reject/{id}")
    ResponseEntity reject(@PathVariable final String id,
                          @RequestBody(required = false) final RejectOfferRequest request) {
        offerApplicationService.reject(id, request.reason);
        return ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable final String id) {
        offerApplicationService.delete(id);
        return ok().build();
    }

    @ExceptionHandler
    ResponseEntity handleInvalidOfferStatusStateException(final InvalidOfferStatusStateException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUserNotFoundException(final UserNotFoundException ex) {
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handle(final OfferNotFoundException ex) {
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @Data
    private static class CreateOfferRequest {

        @NotNull
        private Long userId;

        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }

    @Data
    private static class RejectOfferRequest {

        private String reason;
    }
}
