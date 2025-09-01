package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.event.Event;
import ru.practicum.event.EventRepository;
import ru.practicum.event.dto.EventStatus;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ParametersException;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User author = getUser(userId);
        Event event = getEvent(eventId);

        if (!event.getEventStatus().equals(EventStatus.PUBLISHED)) {
            // По тестам должен возвращаться 400 BAD_REQUEST
            throw new ParametersException("Cannot comment on unpublished event");
        }

        Comment comment = CommentMapper.toComment(newCommentDto);
        comment.setAuthor(author);
        comment.setEvent(event);

        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(savedComment);
    }
    @Override
    public CommentDto getCommentByUser(Long userId, Long commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));

        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException(
                        "Comment with id=" + commentId + " not found for user id=" + userId));

        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> searchCommentsByText(String text, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return commentRepository.searchByText(text, pageable)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long getCommentsCountForEvent(Long eventId) {
        return commentRepository.countByEventId(eventId);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto updateCommentDto) {
        getUser(userId);
        Comment comment = getCommentByIdAndAuthorId(commentId, userId);

        String newText = updateCommentDto.getText();
        if (newText != null && !newText.isBlank()) {
            comment.setContent(newText);
        }

        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        getUser(userId);
        Comment comment = getCommentByIdAndAuthorId(commentId, userId);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        Comment comment = getComment(commentId);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByEventId(Long eventId, Integer from, Integer size) {
        getEvent(eventId);
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return commentRepository.findByEventId(eventId, pageable)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsByUserId(Long userId) {
        getUser(userId);
        return commentRepository.findByAuthorId(userId)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getUserCommentsForEvent(Long userId, Long eventId) {
        getUser(userId);
        getEvent(eventId);
        return commentRepository.findByEventIdAndAuthorId(eventId, userId)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }


    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + commentId + " not found"));
    }

    private Comment getCommentByIdAndAuthorId(Long commentId, Long authorId) {
        return commentRepository.findByIdAndAuthorId(commentId, authorId)
                .orElseThrow(() -> new ParametersException(
                        "Only comment author can modify or delete comment"));
    }
}
