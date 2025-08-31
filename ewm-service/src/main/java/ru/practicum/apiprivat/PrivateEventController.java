package ru.practicum.apiprivat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.*;
import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getAllEventsByUserId(@PathVariable(value = "userId") @Min(1) Long userId,
                                                    @RequestParam(value = "from", defaultValue = "0")
                                                    @PositiveOrZero Integer from,
                                                    @RequestParam(value = "size", defaultValue = "10")
                                                    @Positive Integer size) {
        log.info("GET запрос на получения событий пользователя с id= {}", userId);
        return eventService.getEventsByUserId(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto addEvent(@PathVariable(value = "userId") @Min(1) Long userId,
                                 @RequestBody @Valid NewEventDto input) {
        log.info("POST запрос на создание события от пользователя с id= {}", userId);
        return eventService.addNewEvent(userId, input);
    }

    @GetMapping("/{eventId}")
    public EventDto getFullEventByOwner(@PathVariable(value = "userId") @Min(1) Long userId,
                                            @PathVariable(value = "eventId") @Min(1) Long eventId) {
        log.info("GET запрос на получения полной информации о событии для пользователя с id= {}", userId);
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDto updateEventByOwner(@PathVariable(value = "userId") @Min(0) Long userId,
                                           @PathVariable(value = "eventId") @Min(0) Long eventId,
                                           @RequestBody @Valid UpdateEventUserRequest inputUpdate) {
        log.info("PATCH запрос на обновление события от пользователя с id= {}", userId);
        return eventService.updateEventByUserIdAndEventId(userId, eventId, inputUpdate);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getAllRequestByEventFromOwner(@PathVariable(value = "userId") @Min(1) Long userId,
                                                                       @PathVariable(value = "eventId") @Min(1) Long eventId) {
        log.info("GET запрос на получение информации о всех запросах об участии в событии для пользователя с id= {}", userId);
        return eventService.getAllParticipationRequestsFromEventByOwner(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatusRequestFromOwner(@PathVariable(value = "userId") @Min(1) Long userId,
                                                                       @PathVariable(value = "eventId") @Min(1) Long eventId,
                                                                       @RequestBody EventRequestStatusUpdateRequest inputUpdate) {
        log.info("PATCH запрос на обновление статуса события от пользователя с id= {}", userId);
        return eventService.updateStatusRequest(userId, eventId, inputUpdate);
    }
}