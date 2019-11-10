package com.github.gatoke.offers.domain.user

import com.github.gatoke.offers.domain.shared.EventPublisher
import com.github.gatoke.offers.domain.user.event.UserCreatedEvent
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException
import com.github.gatoke.offers.domain.user.vo.Email
import com.github.gatoke.offers.domain.user.vo.Name
import spock.lang.Specification

import java.lang.Void as Should

class UserDomainServiceTest extends Specification {

    Should "create user when does not exists"() {
        given:
        def userRepositoryMock = Mock(UserRepository)
        def eventPublisherMock = Mock(EventPublisher)
        def service = new UserDomainService(userRepositoryMock, eventPublisherMock)
        and:
        userRepositoryMock.exists(1234L) >> false

        when: "User is created"
        service.createUser(1234L, Name.of("John", "Kowalski"), Email.of("john@kowalski.pl"))

        then: "User is saved to the repository"
        1 * userRepositoryMock.save(_)
        and: "UserCreatedEvent is published"
        1 * eventPublisherMock.publishEvent(_ as UserCreatedEvent)
    }

    Should "fail creating user when already exists"() {
        given:
        def userRepositoryMock = Mock(UserRepository)
        def eventPublisherMock = Mock(EventPublisher)
        def service = new UserDomainService(userRepositoryMock, eventPublisherMock)
        and:
        userRepositoryMock.exists(1234L) >> true

        when: "User is created"
        service.createUser(1234L, Name.of("John", "Kowalski"), Email.of("john@kowalski.pl"))

        then: "UserAlreadyExistsException is thrown"
        thrown(UserAlreadyExistsException)
        and: "User is not saved to the repository"
        0 * userRepositoryMock.save(_)
        and: "UserCreatedEvent is not published"
        0 * eventPublisherMock.publishEvent(_ as UserCreatedEvent)
    }
}
