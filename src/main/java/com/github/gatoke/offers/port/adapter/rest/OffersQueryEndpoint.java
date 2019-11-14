package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.offer.exception.UnknownOfferStatusException;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
class OffersQueryEndpoint {

    private final OfferReadModelRepository offerReadModelRepository;

    @GetMapping("/{id}")
    public ResponseEntity<OfferReadModel> find(@PathVariable final String id) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(UUID.fromString(id));
        return ResponseEntity.ok(offerReadModel);
    }

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", allowMultiple = true),
    })
    public ResponseEntity<Page<OfferReadModel>> findAllWithFilter(@RequestParam(required = false) final String userId,
                                                                  @RequestParam(required = false) final String offerStatus,
                                                                  @ApiIgnore final Pageable pageable) {
        return ResponseEntity.ok(offerReadModelRepository.findAllWithFilter(userId, offerStatus, pageable));
    }

    @ExceptionHandler
    ResponseEntity<String> handleOfferNotFoundException(final OfferNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUnknownOfferStatusException(final UnknownOfferStatusException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserNotFoundException(final UserNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }
}
