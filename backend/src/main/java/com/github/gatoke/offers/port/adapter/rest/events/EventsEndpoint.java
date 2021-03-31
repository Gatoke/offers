package com.github.gatoke.offers.port.adapter.rest.events;

import com.github.gatoke.eventstore.event.StoredEvent;
import com.github.gatoke.eventstore.event.StoredEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
class EventsEndpoint {

    private final StoredEventRepository storedEventRepository;
    private final EventDtoMapper mapper;

    @GetMapping
    List<EventDto> getEvents(@RequestParam(required = false) final UUID after,
                             @RequestParam(required = false, defaultValue = "100") final long limit) {
        final List<StoredEvent> events;
        if (after == null) {
            events = storedEventRepository.getFromBeginning(limit);
        } else {
            events = storedEventRepository.getAfter(after, limit);
        }
        return events.stream().map(mapper::toEventDto).collect(toList());
    }
}
