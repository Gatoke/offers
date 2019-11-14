package com.github.gatoke.offers.application;

import com.github.gatoke.offers.application.dto.UserDto;
import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.UserDomainService;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserDomainService userDomainService;

    public UserDto create(final long userId, final String firstName, final String lastName, final String email) {
        final Name nameVO = Name.of(firstName, lastName);
        final Email emailVO = Email.of(email);
        final User user = userDomainService.createUser(userId, nameVO, emailVO);
        return new UserDto(
                user.getId(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getEmail().getValue()
        );
    }
}
