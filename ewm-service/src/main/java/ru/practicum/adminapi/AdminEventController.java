package ru.practicum.adminapi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.EventService;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventFullDtoForAdmin;
import ru.practicum.event.dto.SearchEventParamsAdmin;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminEventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventFullDtoForAdmin>> searchEvents(
            @Valid SearchEventParamsAdmin params) {
        log.info("GET запрос на получение списка событий");
        List<EventFullDtoForAdmin> list = eventService.getAllEventFromAdmin(params);
        return ResponseEntity.ok(list != null ? list : List.of());
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEventByAdmin(
            @PathVariable @Min(1) Long eventId,
            @RequestBody @Valid UpdateEventAdminRequest inputUpdate) {
        log.info("PATCH запрос на обновление события");
        EventDto updated = eventService.updateEventFromAdmin(eventId, inputUpdate);
        return ResponseEntity.ok(updated);
    }
}