package com.rocketseat.passin.service;

import com.rocketseat.passin.domain.attendee.Attendee;
import com.rocketseat.passin.domain.attendee.exceptions.EventFullException;
import com.rocketseat.passin.domain.event.Event;
import com.rocketseat.passin.domain.event.exceptions.EventNotFoundException;
import com.rocketseat.passin.dto.attendee.AttendeeIdDTO;
import com.rocketseat.passin.dto.attendee.AttendeeRequestDto;
import com.rocketseat.passin.dto.event.EventIdDto;
import com.rocketseat.passin.dto.event.EventRequestDto;
import com.rocketseat.passin.dto.event.EventResponseDto;
import com.rocketseat.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDto getEventDetail(String eventId) {
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDto(event, attendeeList.size());
    }

    public EventIdDto createEvent(EventRequestDto eventDto) {
        Event newEvent = new Event();
        newEvent.setTitle(eventDto.title());
        newEvent.setDateHour(eventDto.dateHour());
        newEvent.setDetails(eventDto.details());
        newEvent.setMaximumAttendees(eventDto.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDto.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDto(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDto attendeeRequestDto) {
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDto.email(), eventId);

        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDto.name());
        newAttendee.setEmail(attendeeRequestDto.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event not found with ID: " + eventId));
    }

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
