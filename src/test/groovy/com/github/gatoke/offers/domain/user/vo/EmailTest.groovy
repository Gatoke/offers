package com.github.gatoke.offers.domain.user.vo

import com.github.gatoke.offers.domain.user.exception.InvalidEmailException
import spock.lang.Specification

import java.lang.Void as Should

class EmailTest extends Specification {

    Should 'create Email'() {
        when: 'Email is created'
        def result = Email.of(givenEmail)

        then: 'No exception is thrown'
        noExceptionThrown()
        and: 'The value is set correctly'
        result.value == expectedResult

        where:
        givenEmail                                  | expectedResult
        'jacob47@gmail.com'                         | 'jacob47@gmail.com'
        'bfdasjiewiorwqerhnihqwr1234.2222222@o2.pl' | 'bfdasjiewiorwqerhnihqwr1234.2222222@o2.pl'
        'mail@microsoft.net'                        | 'mail@microsoft.net'
    }

    Should 'fail on invalid Email'() {
        when: 'Email is created with invalid value'
        Email.of(givenEmail)

        then: 'The exception is thrown'
        thrown(expectedException)

        where:
        givenEmail         | expectedException
        'jacob47@gmail...' | InvalidEmailException
        'karol2.pl'        | InvalidEmailException
        ''                 | InvalidEmailException
        null               | InvalidEmailException
    }

    Should 'test equals and hashCode'() {
        when:
        def e1 = Email.of('jason@gmail.com')
        def e2 = Email.of('jason@gmail.com')

        then:
        e1 == e2 && e2 == e1
        and:
        e1.hashCode() == e2.hashCode()
    }
}
