package com.rocketseat.passin.controllers;

import com.rocketseat.passin.domain.event.Event;
import com.rocketseat.passin.dto.attendee.AttendeeIdDTO;
import com.rocketseat.passin.dto.attendee.AttendeeRequestDto;
import com.rocketseat.passin.dto.attendee.AttendeesListResponseDto;
import com.rocketseat.passin.dto.event.EventIdDto;
import com.rocketseat.passin.dto.event.EventRequestDto;
import com.rocketseat.passin.dto.event.EventResponseDto;
import com.rocketseat.passin.service.AttendeeService;
import com.rocketseat.passin.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final AttendeeService attendeeService;


    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable String id) {
        EventResponseDto event = this.service.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/")
    public List<Event> getAllEvents(){
        return service.listEvents();
    }

    @PostMapping
    public ResponseEntity<EventIdDto> createEvent(@RequestBody EventRequestDto body, UriComponentsBuilder uriBuilder) {
        EventIdDto eventIdDto = this.service.createEvent(body);

        var uri = uriBuilder.path("/events/{id}").buildAndExpand(eventIdDto.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDto);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDto body, UriComponentsBuilder uriBuilder) {
        AttendeeIdDTO attendeeIdDTO = this.service.registerAttendeeOnEvent(eventId, body);


        var uri = uriBuilder.path("/attendees/{attendId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDto> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDto attendeesListResponse = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeesListResponse);
    }
}
