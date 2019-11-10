package com.github.gatoke.offers.port.adapter.persistence.user;


import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "app_user")
class PersistableUser {

    @Id
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private OffsetDateTime createdAt;

    static PersistableUser of(final User user) {
        final PersistableUser persistableUser = new PersistableUser();
        persistableUser.id = user.getId();
        persistableUser.firstName = user.getName().getFirstName();
        persistableUser.lastName = user.getName().getLastName();
        persistableUser.email = user.getEmail().getValue();
        persistableUser.createdAt = user.getCreatedAt().getValue();
        return persistableUser;
    }

    User toDomainObject() {
        return User.builder()
                   .id(this.id)
                   .name(Name.of(this.firstName, this.lastName))
                   .email(Email.of(this.email))
                   .createdAt(Time.of(createdAt))
                   .build();
    }
}
