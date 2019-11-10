package com.github.gatoke.offers.port.adapter.persistence.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface EventJpaRepository  extends JpaRepository<PersistableEvent, UUID> {
}
