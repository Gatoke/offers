package com.github.gatoke.offers.port.adapter.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

public class ResourceCreatedResponseBuilder {

    public static ResponseEntity<?> fromId(final Object id) {
        final String contextPath = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .getPath();
        return status(CREATED).location(
                URI.create(contextPath == null ? "" : contextPath)
        ).build();
    }
}
