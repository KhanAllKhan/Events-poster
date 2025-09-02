package ru.practicum.comment;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

import java.util.List;
import java.util.Map;

public interface CommentService {
    CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto updateCommentDto);

    void deleteComment(Long userId, Long commentId);

    void deleteCommentByAdmin(Long commentId);

    CommentDto getCommentById(Long commentId);

    List<CommentDto> getCommentsByEventId(Long eventId, Integer from, Integer size);

    List<CommentDto> getCommentsByUserId(Long userId);

    List<CommentDto> getUserCommentsForEvent(Long userId, Long eventId);

    List<CommentDto> searchCommentsByText(String text, Integer from, Integer size);

    Long getCommentCountForEvent(Long eventId);

    Map<Long, Long> getCommentCountsForEvents(List<Long> eventIds);

    CommentDto getCommentByUser(Long userId, Long commentId);
}