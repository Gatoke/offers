package com.github.gatoke.offers.port.adapter;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@UtilityClass
public class EntityCreatedResponseFactory {

    public static ResponseEntity created(final Long id) {
        return ResponseEntity.status(CREATED).body(new CreatedBody(id.toString()));
    }

    public static ResponseEntity created(final UUID id) {
        return ResponseEntity.status(CREATED).body(new CreatedBody(id.toString()));
    }

    @Getter
    private static class CreatedBody {

        private final String id;

        private CreatedBody(final String id) {
            this.id = id;
        }
    }
}
