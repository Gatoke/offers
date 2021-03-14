package com.github.gatoke.offers.port.adapter.persistence.user;


import com.github.gatoke.offers.domain.shared.Time;
import com.github.gatoke.offers.domain.user.User;
import com.github.gatoke.offers.domain.user.vo.Email;
import com.github.gatoke.offers.domain.user.vo.Name;
import com.github.gatoke.offers.domain.user.vo.UserId;
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

    private OffsetDateTime registeredAt;

    static PersistableUser of(final User user) {
        final PersistableUser persistableUser = new PersistableUser();
        persistableUser.id = user.getId().getValue();
        persistableUser.firstName = user.getName().getFirstName();
        persistableUser.lastName = user.getName().getLastName();
        persistableUser.email = user.getEmail().getValue();
        persistableUser.registeredAt = user.getRegisteredAt().getValue();
        return persistableUser;
    }

    User toDomainObject() {
        return User.builder()
                .id(UserId.of(this.id))
                .name(Name.of(this.firstName, this.lastName))
                .email(Email.of(this.email))
                .registeredAt(Time.of(registeredAt))
                .build();
    }
}
