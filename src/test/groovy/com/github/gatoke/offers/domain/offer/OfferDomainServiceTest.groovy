package com.github.gatoke.offers.domain.offer

import com.github.gatoke.offers.domain.offer.event.OfferCreatedEvent
import com.github.gatoke.offers.domain.shared.EventPublisher
import com.github.gatoke.offers.domain.user.UserRepository
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException
import spock.lang.Specification
import java.lang.Void as Should

class OfferDomainServiceTest extends Specification {

    Should "create offer"() {
        given:
        def offerRepository = Mock(OfferRepository)
        def userRepository = Mock(UserRepository)
        def eventPublisher = Mock(EventPublisher)
        def service = new OfferDomainService(offerRepository, userRepository, eventPublisher)
        and:
        userRepository.doesNotExist(1234L) >> false

        when: "Offer is created"
        service.createOffer(1234L, "Selling iPhone", "Selling iPhone X 64GB 2000PLN")

        then: "Offer is saved to the repository"
        1 * offerRepository.save(_)
        and: "OfferCreatedEvent is published"
        1 * eventPublisher.publishEvent(_ as OfferCreatedEvent)
    }

    Should "not create offer when user does not exist"() {
        given:
        def offerRepository = Mock(OfferRepository)
        def userRepository = Mock(UserRepository)
        def eventPublisher = Mock(EventPublisher)
        def service = new OfferDomainService(offerRepository, userRepository, eventPublisher)
        and:
        userRepository.doesNotExist(1234L) >> true

        when: "Offer is created"
        service.createOffer(1234L, "Selling iPhone", "Selling iPhone X 64GB 2000PLN")

        then: "UserNotFoundException is thrown"
        thrown(UserNotFoundException)
        and: "Offer is not saved to the repository"
        0 * offerRepository.save(_)
        and: "OfferCreatedEvent is not published"
        0 * eventPublisher.publishEvent(_ as OfferCreatedEvent)
    }
}
