package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.application.dto.OfferDto;
import com.github.gatoke.offers.domain.offer.exception.InvalidOfferStatusStateException;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
class OffersCommandEndpoint {

    private final OfferApplicationService offerApplicationService;

    @PostMapping
    public ResponseEntity<OfferDto> create(@RequestBody @Valid final CreateOfferRequest request) {
        final OfferDto offer = offerApplicationService.create(request.userId, request.title, request.content);
        return status(CREATED).body(offer);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<Object> accept(@PathVariable final String id) {
        offerApplicationService.accept(id);
        return ok().build();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<Object> reject(@PathVariable final String id,
                                         @RequestBody @Valid final RejectOfferRequest request) {
        offerApplicationService.reject(id, request.reason);
        return ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable final String id) {
        offerApplicationService.delete(id);
        return noContent().build();
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidOfferStatusStateException(final InvalidOfferStatusStateException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserNotFoundException(final UserNotFoundException ex) {
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handle(final OfferNotFoundException ex) {
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
