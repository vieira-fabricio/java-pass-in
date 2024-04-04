package com.rocketseat.passin.dto.event;

import com.rocketseat.passin.domain.event.Event;
import lombok.Getter;

@Getter
public class EventResponseDto {
    EventDetailDto eventDetail;

    public EventResponseDto(Event event, Integer numberOfAttendees){
        this.eventDetail = new EventDetailDto(event.getId(),
                event.getTitle(), event.getDetails(), event.getSlug(),
                event.getMaximumAttendees(), numberOfAttendees);
    }
}
