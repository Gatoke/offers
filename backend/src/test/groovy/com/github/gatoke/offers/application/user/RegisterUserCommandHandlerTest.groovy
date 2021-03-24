package com.github.gatoke.offers.application.user

import com.github.gatoke.offers.application.user.command.RegisterUserCommand
import com.github.gatoke.offers.domain.shared.EventType
import com.github.gatoke.offers.domain.user.User
import com.github.gatoke.offers.domain.user.UserRepository
import com.github.gatoke.offers.domain.user.vo.UserId
import spock.lang.Specification

class RegisterUserCommandHandlerTest extends Specification {

    def "should register user"() {
        given:
        def userRepositoryMock = new TestUserRepository()
        def commandHandler = new RegisterUserCommandHandler(userRepositoryMock)
        and:
        userRepositoryMock.exists(UserId.from(1234L)) >> false

        when:
        def result = commandHandler.execute(
                new RegisterUserCommand(1234L, 'John', 'Kowalski', 'john@kowalski.pl')
        )

        then:
        result.size() == 1
        and:
        result.first().eventType == EventType.USER_REGISTERED
    }


    private class TestUserRepository implements UserRepository {

        private List<User> users = new ArrayList<>()

        @Override
        User save(User user) {
            users.add(user)
            return user
        }

        @Override
        boolean doesNotExist(UserId userId) {
            return false
        }

        @Override
        boolean exists(UserId userId) {
            return false
        }
    }
}
