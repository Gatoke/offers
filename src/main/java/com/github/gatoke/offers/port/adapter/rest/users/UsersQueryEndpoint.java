package com.github.gatoke.offers.port.adapter.rest.users;

import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModel;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UsersQueryEndpoint {

    private final UserReadModelRepository userReadModelRepository;

    @GetMapping("/{id}")
    ResponseEntity<UserReadModel> get(@PathVariable final String id) {
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(id);
        return ResponseEntity.ok(userReadModel);
    }
}
