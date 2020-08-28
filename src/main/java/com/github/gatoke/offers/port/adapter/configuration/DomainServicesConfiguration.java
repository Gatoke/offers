package com.github.gatoke.offers.port.adapter.configuration;

import com.github.gatoke.offers.domain.offer.FindOutdatedOffersService;
import com.github.gatoke.offers.domain.offer.OfferRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DomainServicesConfiguration {

    @Bean
    FindOutdatedOffersService findOutdatedOffersService(final OfferRepository offerRepository) {
        return new FindOutdatedOffersService(offerRepository);
    }
}
