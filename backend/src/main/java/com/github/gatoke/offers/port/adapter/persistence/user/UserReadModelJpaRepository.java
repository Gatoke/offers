package com.github.gatoke.offers.port.adapter.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserReadModelJpaRepository extends JpaRepository<UserReadModel, Long> {
}
