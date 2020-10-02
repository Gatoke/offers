package com.github.gatoke.offers.port.adapter.rest.events;

import com.github.gatoke.offers.port.adapter.persistence.event.EventDto;
import com.github.gatoke.offers.port.adapter.persistence.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
class EventsEndpoint {

    private final EventRepository eventRepository;

    @GetMapping
    List<EventDto> getEvents(@RequestParam(required = false) final UUID after) {
        if (after == null) {
            return eventRepository.getPageFromBeginning();
        }
        return eventRepository.getPageAfterId(after);
    }
}
