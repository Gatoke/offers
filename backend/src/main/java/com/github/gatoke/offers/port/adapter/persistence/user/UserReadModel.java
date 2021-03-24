package com.github.gatoke.offers.port.adapter.persistence.user;

import com.github.gatoke.offers.domain.user.event.UserRegisteredEvent;
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

    static UserReadModel from(final UserRegisteredEvent event) {
        final UserReadModel userReadModel = new UserReadModel();
        userReadModel.id = event.getId();
        userReadModel.firstName = event.getFirstName();
        userReadModel.lastName = event.getLastName();
        userReadModel.email = event.getEmail();
        userReadModel.registeredAt = event.getRegisteredAt();
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
