package com.github.gatoke.offers.application


import spock.lang.Specification

class UserApplicationServiceTest extends Specification {

//    Void "create user when does not exists"() {
//        given:
//        def userRepositoryMock = new TestUserRepository()
//        def eventPublisherMock = Mock(EventPublisher)
//        def service = new UserApplicationService(userRepositoryMock, eventPublisherMock)
//        and:
//        userRepositoryMock.exists(1234L) >> false
//
//        when: "User is created"
//        service.registerUser(
//                new RegisterUserCommand(1234L, 'John', 'Kowalski', 'john@kowalski.pl')
//        )
//
//        then: "UserRegisteredEvent is published"
//        1 * eventPublisherMock.publish(_ as UserRegisteredEvent)
//    }
//
//    Void "fail creating user when already exists"() {
//        given:
//        def userRepositoryMock = Mock(UserRepository)
//        def eventPublisherMock = Mock(EventPublisher)
//        def service = new UserApplicationService(userRepositoryMock, eventPublisherMock)
//        and:
//        userRepositoryMock.exists(1234L) >> true
//
//        when: "User is created"
//        service.registerUser(
//                new RegisterUserCommand(1234L, 'John', 'Kowalski', 'john@kowalski.pl')
//        )
//
//        then: "UserAlreadyExistsException is thrown"
//        thrown(UserAlreadyExistsException)
//        and: "User is not saved to the repository"
//        0 * userRepositoryMock.save(_)
//        and: "UserRegisteredEvent is not published"
//        0 * eventPublisherMock.publish(_ as UserRegisteredEvent)
//    }
//
//    private class TestUserRepository implements UserRepository {
//
//        private List<User> users = new ArrayList<>()
//
//        @Override
//        User save(User user) {
//            users.add(user)
//            return user
//        }
//
//        @Override
//        boolean doesNotExist(long userId) {
//            return false
//        }
//
//        @Override
//        boolean exists(long userId) {
//            return false
//        }
//    }
}
