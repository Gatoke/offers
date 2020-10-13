package com.github.gatoke.offers.port.adapter.rest.offers;

import com.github.gatoke.offers.application.OfferApplicationService;
import com.github.gatoke.offers.application.command.AcceptOfferCommand;
import com.github.gatoke.offers.application.command.CreateOfferCommand;
import com.github.gatoke.offers.application.command.DeleteOfferCommand;
import com.github.gatoke.offers.application.command.RejectOfferCommand;
import com.github.gatoke.offers.application.dto.OfferDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
class OffersCommandEndpoint {

    private final OfferApplicationService offerApplicationService;

    @PostMapping
    ResponseEntity<OfferDto> create(@RequestBody @Valid final CreateOfferRequest request) {
        final OfferDto offer = offerApplicationService.createOffer(request.toCommand());
        return status(CREATED).body(offer);
    }

    @PostMapping("/accept/{offerId}")
    ResponseEntity<Object> accept(@PathVariable final String offerId) {
        offerApplicationService.accept(new AcceptOfferCommand(offerId));
        return ok().build();
    }

    @PostMapping("/reject")
    ResponseEntity<Object> reject(@RequestBody @Valid final RejectOfferRequest request) {
        offerApplicationService.reject(request.toCommand());
        return ok().build();
    }

    @DeleteMapping("/{offerId}")
    ResponseEntity<Object> delete(@PathVariable final String offerId) {
        offerApplicationService.delete(new DeleteOfferCommand(offerId));
        return noContent().build();
    }

    @Data
    private static class CreateOfferRequest {

        @NotNull
        private Long userId;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        CreateOfferCommand toCommand() {
            return new CreateOfferCommand(userId, title, content);
        }
    }

    @Data
    private static class RejectOfferRequest {

        @NotBlank
        private String offerId;

        private String reason;

        RejectOfferCommand toCommand() {
            return new RejectOfferCommand(offerId, reason);
        }
    }
}
