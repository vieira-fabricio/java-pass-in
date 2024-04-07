package com.rocketseat.passin.controllers;

import com.rocketseat.passin.dto.attendee.AttendeeBadgetDTO;
import com.rocketseat.passin.dto.attendee.AttendeeBadgetResponseDTO;
import com.rocketseat.passin.service.AttendeeService;
import com.rocketseat.passin.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;
    private final CheckInService checkInService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgetResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriBuilder) {
        AttendeeBadgetResponseDTO response = this.attendeeService.getAttendeeBadge(attendeeId, uriBuilder);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriBuilder) {
        this.attendeeService.checkInAttendee(attendeeId);

        var uri = uriBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }

}
