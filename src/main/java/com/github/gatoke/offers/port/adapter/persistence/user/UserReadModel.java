package com.github.gatoke.offers.port.adapter.persistence.user;

import com.github.gatoke.offers.domain.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "user_read_model")
public class UserReadModel {

    @Id
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private OffsetDateTime registeredAt;

    private long activeOffersCount;

    static UserReadModel of(final User user) {
        final UserReadModel userReadModel = new UserReadModel();
        userReadModel.id = user.getId();
        userReadModel.firstName = user.getName().getFirstName();
        userReadModel.lastName = user.getName().getLastName();
        userReadModel.email = user.getEmail().getValue();
        userReadModel.registeredAt = user.getRegisteredAt().getValue();
        userReadModel.activeOffersCount = 0;
        return userReadModel;
    }

    public void increaseActiveOffersCount() {
        activeOffersCount++;
    }

    public void decreaseActiveOffersCount() {
        activeOffersCount--;
    }
}
