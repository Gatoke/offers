package com.github.gatoke.offers.port.adapter.rest.offers;

import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModel;
import com.github.gatoke.offers.port.adapter.persistence.offer.OfferReadModelRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
class OffersQueryEndpoint {

    private final OfferReadModelRepository offerReadModelRepository;

    @GetMapping("/{id}")
    ResponseEntity<OfferReadModel> find(@PathVariable final String id) {
        final OfferReadModel offerReadModel = offerReadModelRepository.findOrThrow(UUID.fromString(id));
        return ResponseEntity.ok(offerReadModel);
    }

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", allowMultiple = true),
    })
    ResponseEntity<Page<OfferReadModel>> findAllWithFilter(@RequestParam(required = false) final String userId,
                                                           @RequestParam(required = false) final String offerStatus,
                                                           @ApiIgnore final Pageable pageable) {
        return ResponseEntity.ok(offerReadModelRepository.findAllWithFilter(userId, offerStatus, pageable));
    }
}
