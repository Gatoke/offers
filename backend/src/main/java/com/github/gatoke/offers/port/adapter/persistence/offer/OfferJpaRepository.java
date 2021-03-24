package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

interface OfferJpaRepository extends JpaRepository<PersistableOffer, UUID> {

    List<PersistableOffer> findAllByCreatedAtBeforeAndStatusEquals(OffsetDateTime offsetDateTime, OfferStatus offerStatus);
}
