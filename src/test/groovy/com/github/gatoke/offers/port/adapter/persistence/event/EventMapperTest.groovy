package com.github.gatoke.offers.port.adapter.persistence.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.gatoke.offers.domain.offer.event.OfferPublishedEvent
import com.github.gatoke.offers.domain.offer.vo.OfferStatus
import com.github.gatoke.offers.domain.shared.EventType
import com.github.gatoke.offers.domain.shared.Time
import spock.lang.Specification

import java.time.OffsetDateTime

class EventMapperTest extends Specification {

    def eventMapper = new EventMapper(new ObjectMapper())

    static def ID = UUID.randomUUID()
    static def EVENT_TYPE = EventType.OFFER_PUBLISHED
    static def OCCURRED_ON = OffsetDateTime.now()
    static def PAYLOAD = """
{
    "offerId": "f3bdf59c-c372-492f-91c3-21cf55db058e",
    "status": "PUBLISHED"
}
"""

    def shouldMapToDomainEvent() {
        given:
        def persistableEvent = new EventLog(ID, EVENT_TYPE, OCCURRED_ON, PAYLOAD)

        when:
        def actual = eventMapper.toDomainEvent(persistableEvent)

        then:
        actual == expected()
    }


    def expected() {
        return new OfferPublishedEvent(
                id: ID,
                occurredOn: Time.of(OCCURRED_ON),
                payload: [
                        offerId: UUID.fromString('f3bdf59c-c372-492f-91c3-21cf55db058e'),
                        status : OfferStatus.PUBLISHED
                ]
        )
    }
}
