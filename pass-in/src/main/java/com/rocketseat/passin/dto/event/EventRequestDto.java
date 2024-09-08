package com.rocketseat.passin.dto.event;

import java.time.LocalDateTime;

public record EventRequestDto(String title, LocalDateTime dateHour, String details, Integer maximumAttendees) {

}
