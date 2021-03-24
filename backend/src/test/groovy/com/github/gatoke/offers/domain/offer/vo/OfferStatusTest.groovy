package com.github.gatoke.offers.domain.offer.vo

import com.github.gatoke.offers.domain.offer.exception.UnknownOfferStatusException
import spock.lang.Specification

import java.lang.Void as Should

class OfferStatusTest extends Specification {

    Should 'create OfferStatus'() {
        expect:
        OfferStatus.from(givenStatus)

        where:
        givenStatus       | expectedResult
        'PUBLISHED'       | OfferStatus.PUBLISHED
        'pending'         | OfferStatus.PENDING
        'aCcEpTed'        | OfferStatus.ACCEPTED
        ' E X P I R E D ' | OfferStatus.EXPIRED
    }

    Should 'fail on invalid OfferStatus'() {
        when: 'OfferStatus is created with invalid value'
        OfferStatus.from(givenStatus)

        then: 'The exception is thrown'
        thrown(expectedException)

        where:
        givenStatus | expectedException
        null        | UnknownOfferStatusException
        ''          | UnknownOfferStatusException
        'Abc123'    | UnknownOfferStatusException
    }
}
