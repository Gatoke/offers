package com.github.gatoke.offers.port.adapter.persistence.offer;

import com.github.gatoke.offers.domain.offer.vo.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

interface OfferReadModelJpaRepository extends JpaRepository<OfferReadModel, UUID> {

    @Query("SELECT o FROM OfferReadModel o WHERE (:userId is null or o.userId = :userId) and (:status is null"
            + " or o.status = :status)")
    List<OfferReadModel> findAllWithFilter(@Param("userId") Long userId, @Param("status") OfferStatus status);
}
