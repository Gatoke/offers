package com.github.gatoke.offers.port.adapter.configuration;

import com.github.gatoke.offers.domain.offer.OfferDomainService;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import com.github.gatoke.offers.domain.shared.EventPublisher;
import com.github.gatoke.offers.domain.user.UserDomainService;
import com.github.gatoke.offers.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DomainServicesConfiguration {

    @Bean
    UserDomainService userDomainService(final UserRepository userRepository, final EventPublisher eventPublisher) {
        return new UserDomainService(userRepository, eventPublisher);
    }

    @Bean
    OfferDomainService offerDomainService(final OfferRepository offerRepository,
                                          final UserRepository userRepository,
                                          final EventPublisher eventPublisher) {
        return new OfferDomainService(offerRepository, userRepository, eventPublisher);
    }
}
