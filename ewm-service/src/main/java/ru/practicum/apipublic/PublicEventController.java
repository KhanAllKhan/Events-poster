package ru.practicum.apipublic;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.EventService;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.SearchEventParams;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/events")
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getAllEvents(@Valid SearchEventParams searchEventParams,
                                            HttpServletRequest request) {
        log.info("GET запрос на получения событий с фильтром");
        return eventService.getAllEventFromPublic(searchEventParams, request);
    }

    @GetMapping("/{eventId}")
    public EventDto getEventById(@PathVariable(value = "eventId") @Min(1) Long eventId,
                                     HttpServletRequest request) {
        log.info("GET запрос на получения полной информации о событии с  id= {}", eventId);
        return eventService.getEventById(eventId, request);
    }
}