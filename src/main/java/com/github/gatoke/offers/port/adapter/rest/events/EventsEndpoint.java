package com.github.gatoke.offers.port.adapter.rest.events;

import com.github.gatoke.offers.port.adapter.persistence.event.EventRepository;
import com.github.gatoke.offers.port.adapter.persistence.event.PersistableEvent;
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

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @GetMapping
    List<EventDto> getEvents(@RequestParam(required = false) final UUID after) {
        final List<PersistableEvent> events;
        if (after == null) {
            events = eventRepository.getPageFromBeginning();
        } else {
            events = eventRepository.getPageAfterId(after);
        }
        return events.stream()
                .map(eventMapper::toEventDto)
                .collect(toList());
    }
}
