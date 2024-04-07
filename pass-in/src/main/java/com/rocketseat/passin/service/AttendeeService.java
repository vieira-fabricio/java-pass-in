package com.rocketseat.passin.service;

import com.rocketseat.passin.domain.attendee.Attendee;
import com.rocketseat.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.rocketseat.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.rocketseat.passin.domain.checkin.CheckIn;
import com.rocketseat.passin.dto.attendee.AttendeeBadgetResponseDTO;
import com.rocketseat.passin.dto.attendee.AttendeeDetails;
import com.rocketseat.passin.dto.attendee.AttendeesListResponseDto;
import com.rocketseat.passin.dto.attendee.AttendeeBadgetDTO;
import com.rocketseat.passin.repositories.AttendeeRepository;
import com.rocketseat.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDto getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDto(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if (isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        if (newAttendee.getEmail() == null || newAttendee.getEmail().isEmpty()) {
            throw new IllegalArgumentException("The attendee email cannot be null or empty.");
        }
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public AttendeeBadgetResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriBuilder) {
        Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgetDTO attendeeBadgetDTO = new AttendeeBadgetDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());
        return new AttendeeBadgetResponseDTO(attendeeBadgetDTO);
    }

    public void checkInAttendee(String attendeeId) {
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() ->
                new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));
    }
}
