package ru.practicum.apipublic;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.CommentService;
import ru.practicum.comment.dto.CommentDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable @Positive Long commentId) {
        log.info("GET request for comment {}", commentId);
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/events/{eventId}")
    public List<CommentDto> getEventComments(@PathVariable @Positive Long eventId,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET request for comments of event {}", eventId);
        return commentService.getCommentsByEventId(eventId, from, size);
    }
}