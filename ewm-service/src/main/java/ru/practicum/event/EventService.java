package ru.practicum.event;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;
import java.util.List;

public interface EventService {

    List<EventFullDtoForAdmin> getAllEventFromAdmin(SearchEventParamsAdmin searchEventParamsAdmin);

    EventDto updateEventFromAdmin(Long eventId, UpdateEventAdminRequest inputUpdate);

    List<EventShortDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventDto addNewEvent(Long userId, NewEventDto input);

    EventDto getEventByUserIdAndEventId(Long userId, Long eventId);

    EventDto updateEventByUserIdAndEventId(Long userId, Long eventId, UpdateEventUserRequest inputUpdate);

    List<RequestDto> getAllParticipationRequestsFromEventByOwner(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateStatusRequest(Long userId, Long eventId, EventRequestStatusUpdateRequest inputUpdate);

    List<EventShortDto> getAllEventFromPublic(SearchEventParams searchEventParams, HttpServletRequest request);

    EventDto getEventById(Long eventId, HttpServletRequest request);
}