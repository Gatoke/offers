package com.github.gatoke.offers.application.offer

import com.github.gatoke.offers.application.offer.command.CreateOfferCommand
import com.github.gatoke.offers.domain.offer.Offer
import com.github.gatoke.offers.domain.offer.OfferRepository
import com.github.gatoke.offers.domain.offer.vo.OfferId
import com.github.gatoke.offers.domain.shared.EventType
import com.github.gatoke.offers.domain.shared.Time
import com.github.gatoke.offers.domain.user.UserRepository
import com.github.gatoke.offers.domain.user.vo.UserId
import spock.lang.Specification

class CreateOfferCommandHandlerTest extends Specification {

    def "should create offer"() {
        given:
        def offerRepository = new TestOfferRepository()
        def userRepository = Mock(UserRepository)
        def commandHandler = new CreateOfferCommandHandler(offerRepository, userRepository)
        and:
        userRepository.doesNotExist(UserId.of(1234L)) >> false

        when:
        def result = commandHandler.execute(
                new CreateOfferCommand(1234L, 'iPhone7', 'Selling iPhone 7 64GB, good quality')
        )

        then:
        result.size() == 1
        and:
        result.first().type == EventType.OFFER_CREATED
    }

    private class TestOfferRepository implements OfferRepository {

        private List<Offer> offers = new ArrayList<>()

        @Override
        Offer save(Offer offer) {
            offers.add(offer)
            return offer
        }

        @Override
        Offer findOrFail(OfferId offerId) {
            return null
        }

        @Override
        List<Offer> findPublishedOffersCreatedBefore(Time dateTime) {
            return null
        }
    }
}
