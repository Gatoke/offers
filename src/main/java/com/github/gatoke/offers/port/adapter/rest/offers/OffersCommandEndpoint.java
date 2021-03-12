package com.github.gatoke.offers.port.adapter.rest.offers;

import com.github.gatoke.offers.application.offer.command.AcceptOfferCommand;
import com.github.gatoke.offers.application.offer.command.CreateOfferCommand;
import com.github.gatoke.offers.application.offer.command.DeleteOfferCommand;
import com.github.gatoke.offers.application.offer.command.RejectOfferCommand;
import com.github.gatoke.offers.application.shared.CommandBus;
import com.github.gatoke.offers.port.adapter.rest.ResourceCreatedResponseBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
class OffersCommandEndpoint {

    private final CommandBus commandBus;

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid final CreateOfferRequest request) {
        final CreateOfferCommand command = request.toCommand();
        commandBus.execute(command);
        return ResourceCreatedResponseBuilder.fromId(command.getOfferId());
    }

    @PostMapping("/accept/{offerId}")
    void accept(@PathVariable final String offerId) {
        final AcceptOfferCommand command = new AcceptOfferCommand(offerId);
        commandBus.execute(command);
    }

    @PostMapping("/reject")
    void reject(@RequestBody @Valid final RejectOfferRequest request) {
        final RejectOfferCommand command = request.toCommand();
        commandBus.execute(command);
    }

    @DeleteMapping("/{offerId}")
    void delete(@PathVariable final String offerId) {
        final DeleteOfferCommand command = new DeleteOfferCommand(offerId);
        commandBus.execute(command);
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
