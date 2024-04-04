package com.rocketseat.passin.service;

import com.rocketseat.passin.domain.attendee.Attendee;
import com.rocketseat.passin.domain.event.Event;
import com.rocketseat.passin.domain.event.exceptions.EventNotFoundException;
import com.rocketseat.passin.dto.event.EventIdDto;
import com.rocketseat.passin.dto.event.EventRequestDto;
import com.rocketseat.passin.dto.event.EventResponseDto;
import com.rocketseat.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDto getEventDetail(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event not found with ID: " + eventId));
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDto(event, attendeeList.size());
    }

    public EventIdDto createEvent(EventRequestDto eventDto) {
        Event newEvent = new Event();
        newEvent.setTitle(eventDto.title());
        newEvent.setDetails(eventDto.details());
        newEvent.setMaximumAttendees(eventDto.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDto.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDto(newEvent.getId());
    }

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
