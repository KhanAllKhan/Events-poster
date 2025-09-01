package ru.practicum.apiprivat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.CommentService;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/comments")
public class PrivateCommentController {
    private final CommentService commentService;

    @PostMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable @Min(1) Long userId,
                                 @PathVariable @Min(1) Long eventId,
                                 @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("POST request to add comment from user {} to event {}", userId, eventId);
        return commentService.addComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable @Min(1) Long userId,
                                    @PathVariable @Min(1) Long commentId,
                                    @RequestBody @Valid UpdateCommentDto updateCommentDto) {
        log.info("PATCH request to update comment {} from user {}", commentId, userId);
        return commentService.updateComment(userId, commentId, updateCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Min(1) Long userId,
                              @PathVariable @Min(1) Long commentId) {
        log.info("DELETE request to delete comment {} from user {}", commentId, userId);
        commentService.deleteComment(userId, commentId);
    }

    @GetMapping
    public List<CommentDto> getUserComments(@PathVariable @Min(1) Long userId) {
        log.info("GET request for comments by user {}", userId);
        return commentService.getCommentsByUserId(userId);
    }

    @GetMapping("/events/{eventId}")
    public List<CommentDto> getUserCommentsForEvent(@PathVariable @Min(1) Long userId,
                                                    @PathVariable @Min(1) Long eventId) {
        log.info("GET request for comments by user {} for event {}", userId, eventId);
        return commentService.getUserCommentsForEvent(userId, eventId);
    }
}