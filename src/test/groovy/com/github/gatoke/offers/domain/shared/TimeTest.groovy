package com.github.gatoke.offers.domain.shared

import spock.lang.Specification

import java.lang.Void as Should
import java.time.OffsetDateTime
import java.time.ZoneOffset

class TimeTest extends Specification {

    Should 'times with different values not be equal'() {
        when: 'First time is created from OffsetDateTime.now() minus 7 days'
        def firstTime = Time.of(OffsetDateTime.now().minusDays(7))
        and: 'Second time is created from now()'
        def secondTime = Time.now()

        then: 'Times are not equal'
        firstTime != secondTime
    }

    Should 'test equals and hashCode'() {
        when:
        def t1 = Time.of(OffsetDateTime.of(2010, 01, 01, 1, 51, 23, 10, ZoneOffset.MAX))
        def t2 = Time.of(OffsetDateTime.of(2010, 01, 01, 1, 51, 23, 10, ZoneOffset.MAX))

        then:
        t1 == t2 && t2 == t1
        and:
        t1.hashCode() == t2.hashCode()
    }
}
