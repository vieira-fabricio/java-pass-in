package com.rocketseat.passin.controllers;

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
    @PostMapping
    public ResponseEntity<EventIdDto> createEvent(@RequestBody EventRequestDto body, UriComponentsBuilder uriBuilder) {
        EventIdDto eventIdDto = this.service.createEvent(body);

        var uri = uriBuilder.path("/events/{id}").buildAndExpand(eventIdDto.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDto);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDto> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDto attendeesListResponse = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeesListResponse);
    }
}
