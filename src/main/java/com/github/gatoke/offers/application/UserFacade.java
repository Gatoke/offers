package com.github.gatoke.offers.application;

import com.github.gatoke.offers.domain.user.UserDomainService;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacade {

    private final UserDomainService userDomainService;

    public long create(final long userId, final String firstName, final String lastName, final String email) {
        final Name nameVO = Name.of(firstName, lastName);
        final Email emailVO = Email.of(email);
        return userDomainService.createUser(userId, nameVO, emailVO);
    }
}
