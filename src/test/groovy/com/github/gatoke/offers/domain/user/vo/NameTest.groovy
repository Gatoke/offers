package com.github.gatoke.offers.domain.user.vo

import com.github.gatoke.offers.domain.user.exception.InvalidNameException
import spock.lang.Specification

import java.lang.Void as Should

class NameTest extends Specification {

    Should 'create Name'() {
        when: 'Name is created'
        def result = Name.of(givenFirstName, givenLastName)

        then: 'No exception is thrown'
        noExceptionThrown()
        and: 'Values are set correctly'
        result.firstName == expectedFirstName
        result.lastName == expectedLastName

        where:
        givenFirstName | givenLastName                         | expectedFirstName | expectedLastName
        'Steve'        | 'Fox'                                 | 'Steve'           | 'Fox'
        'Tim'          | 'Stadt'                               | 'Tim'             | 'Stadt'
        'Róża'         | 'Wąkołań'                             | 'Róża'            | 'Wąkołań'
        'Hubert'       | 'Wolfeschlegelsteinhausenbergerdorff' | 'Hubert'          | 'Wolfeschlegelsteinhausenbergerdorff'
        '  Julia'      | 'Roberts '                            | 'Julia'           | 'Roberts'
    }

    Should 'fail on invalid Name'() {
        when: 'Name is created with invalid value'
        Name.of(givenFirstName, givenLastName)
        then: 'The exception is thrown'
        thrown(expectedException)

        where:
        givenFirstName | givenLastName | expectedException
        ' T '          | 'Kowalsky'    | InvalidNameException
        'Michael'      | 'J'           | InvalidNameException
        null           | 'Kowalsky'    | InvalidNameException
        'Carol'        | null          | InvalidNameException
        ''''''         | 'Mouse'       | InvalidNameException
    }

    Should 'test equals and hashCode'() {
        when:
        def n1 = Name.of('Jan', 'Kowalski')
        def n2 = Name.of('Jan', 'Kowalski')

        then:
        n1 == n2 && n2 == n1
        and:
        n1.hashCode() == n2.hashCode()
    }
}
