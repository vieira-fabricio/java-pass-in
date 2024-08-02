package com.rocketseat.passin.dto.event;

import java.time.LocalDateTime;

public record EventDetailDto(
        String id,
        String title,
        LocalDateTime dateHour,
        String details,
        String slug,
        Integer maximumAttendees,
        Integer attendeesAmount
){}
