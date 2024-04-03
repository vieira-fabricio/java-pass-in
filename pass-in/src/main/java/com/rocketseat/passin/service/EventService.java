package com.rocketseat.passin.service;

import com.rocketseat.passin.domain.event.Event;
import com.rocketseat.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public void getEventDetail(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElseThrow(() ->
                new RuntimeException("Event not found with ID: " + eventId));
        return;
    }
}
