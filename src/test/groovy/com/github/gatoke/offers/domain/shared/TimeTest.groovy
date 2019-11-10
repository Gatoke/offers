package com.github.gatoke.offers.domain.shared

import spock.lang.Specification
import java.lang.Void as Should
import java.time.OffsetDateTime
import java.time.ZoneOffset

class TimeTest extends Specification {

    Should 'times with the same values be equal'() {
        setup: 'There is an OffsetDateTime'
        def offsetDateTime = OffsetDateTime.of(2010, 01, 01, 1, 51, 23, 10, ZoneOffset.MAX)

        when: 'First time is created from the OffsetDateTime'
        def firstTime = Time.of(offsetDateTime)
        and: 'Second time is created from new OffsetDateTime with the same value'
        def secondTime = Time.of(OffsetDateTime.of(2010, 01, 01, 1, 51, 23, 10, ZoneOffset.MAX))

        then: 'Both times are equal'
        firstTime == secondTime
    }

    Should 'times with different values not be equal'() {
        when: 'First time is created from OffsetDateTime.now() minus 7 days'
        def firstTime = Time.of(OffsetDateTime.now().minusDays(7))
        and: 'Second time is created from now()'
        def secondTime = Time.now()

        then: 'Times are not equal'
        firstTime != secondTime
    }
}
