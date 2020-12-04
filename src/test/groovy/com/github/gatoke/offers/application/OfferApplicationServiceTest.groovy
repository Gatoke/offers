package com.github.gatoke.offers.application

import com.github.gatoke.offers.application.command.CreateOfferCommand
import com.github.gatoke.offers.domain.offer.Offer
import com.github.gatoke.offers.domain.offer.OfferRepository
import com.github.gatoke.offers.domain.offer.event.OfferCreatedEvent
import com.github.gatoke.offers.domain.shared.EventPublisher
import com.github.gatoke.offers.domain.user.UserRepository
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException
import spock.lang.Specification

import java.time.OffsetDateTime

class OfferApplicationServiceTest extends Specification {

    Void "create offer"() {
        given:
        def offerRepository = new TestOfferRepository()
        def userRepository = Mock(UserRepository)
        def eventPublisher = Mock(EventPublisher)
        def service = new OfferApplicationService(offerRepository, userRepository, eventPublisher)
        and:
        userRepository.doesNotExist(1234L) >> false

        when: "Offer is created"
        service.createOffer(
                new CreateOfferCommand(1234L, "Selling iPhone", "Selling iPhone X 64GB 2000PLN")
        )

        then: "OfferCreatedEvent is published"
        1 * eventPublisher.publish(_ as OfferCreatedEvent)
    }

    Void "not create offer when user does not exist"() {
        given:
        def offerRepository = Mock(OfferRepository)
        def userRepository = Mock(UserRepository)
        def eventPublisher = Mock(EventPublisher)
        def service = new OfferApplicationService(offerRepository, userRepository, eventPublisher)
        and:
        userRepository.doesNotExist(1234L) >> true

        when: "Offer is created"
        service.createOffer(
                new CreateOfferCommand(1234L, "Selling iPhone", "Selling iPhone X 64GB 2000PLN")
        )

        then: "UserNotFoundException is thrown"
        thrown(UserNotFoundException)
        and: "Offer is not saved to the repository"
        0 * offerRepository.save(_)
        and: "OfferCreatedEvent is not published"
        0 * eventPublisher.publish(_ as OfferCreatedEvent)
    }

    private class TestOfferRepository implements OfferRepository {

        private List<Offer> offers = new ArrayList<>()

        @Override
        Offer save(Offer offer) {
            offers.add(offer)
            return offer
        }

        @Override
        Offer findOrFail(UUID offerId) {
            return null
        }

        @Override
        List<Offer> findPublishedOffersCreatedBefore(OffsetDateTime dateTime) {
            return null
        }
    }
}
