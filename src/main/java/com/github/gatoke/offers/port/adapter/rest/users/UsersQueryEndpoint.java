package com.github.gatoke.offers.port.adapter.rest.users;

import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModel;
import com.github.gatoke.offers.port.adapter.persistence.user.UserReadModelRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UsersQueryEndpoint {

    private final UserReadModelRepository userReadModelRepository;

    @GetMapping("/{id}")
    ResponseEntity<UserReadModel> get(@PathVariable final long id) {
        final UserReadModel userReadModel = userReadModelRepository.findOrThrow(id);
        return ResponseEntity.ok(userReadModel);
    }

    @GetMapping()
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", allowMultiple = true),
    })
    ResponseEntity<Page<UserReadModel>> findAll(@ApiIgnore final Pageable pageable) {
        final Page<UserReadModel> userReadModel = userReadModelRepository.findAll(pageable);
        return ResponseEntity.ok(userReadModel);
    }
}
