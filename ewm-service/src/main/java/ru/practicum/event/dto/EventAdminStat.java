package ru.practicum.event.dto;

import java.util.Optional;

public enum EventAdminStat {
    PUBLISH_EVENT, REJECT_EVENT;

    public static Optional<EventAdminStat> from(String stringState) {
        for (EventAdminStat state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}