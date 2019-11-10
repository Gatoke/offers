package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.offer.exception.UnknownOfferStatusException;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
class OfferQueryEndpoint {

    private final OfferReadModelRepository offerReadModelRepository;

    @GetMapping("/{id}")
    ResponseEntity find(@PathVariable final String id) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(UUID.fromString(id));
        return ResponseEntity.ok(offerReadModel);
    }

    @GetMapping
    ResponseEntity findAllWithFilter(@RequestParam(required = false) final String userId,
                                     @RequestParam(required = false) final String offerStatus) {
        return ResponseEntity.ok(offerReadModelRepository.findAllWithFilter(userId, offerStatus));
    }

    @ExceptionHandler
    ResponseEntity handleOfferNotFoundException(final OfferNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUnknownOfferStatusException(final UnknownOfferStatusException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUserNotFoundException(final UserNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }
}
