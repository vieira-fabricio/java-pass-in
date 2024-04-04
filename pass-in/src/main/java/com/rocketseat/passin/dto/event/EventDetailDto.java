package com.rocketseat.passin.dto.event;

public record EventDetailDto(
        String id,
        String title,
        String details,
        String slug,
        Integer maximumAttendees,
        Integer attendeesAmount
){}
