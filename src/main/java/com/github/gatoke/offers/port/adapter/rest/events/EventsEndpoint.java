package com.github.gatoke.offers.port.adapter.rest.events;

import com.github.gatoke.offers.eventstore.event.EventLogRepository;
import com.github.gatoke.offers.eventstore.event.StoredEvent;
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

    private final EventLogRepository eventLogRepository;
    private final EventDtoMapper mapper;

    @GetMapping
    List<EventDto> getEvents(@RequestParam(required = false) final UUID after) {
        final List<StoredEvent> events;
        if (after == null) {
            events = eventLogRepository.getPageFromBeginning();
        } else {
            events = eventLogRepository.getPageAfterId(after);
        }
        return events.stream().map(mapper::toEventDto).collect(toList());
    }
}
