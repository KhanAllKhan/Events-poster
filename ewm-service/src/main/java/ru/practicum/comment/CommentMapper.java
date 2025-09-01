package ru.practicum.comment;

import lombok.experimental.UtilityClass;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.user.UserMapper;

@UtilityClass
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .eventId(comment.getEvent().getId())
                .createdDate(comment.getCreatedDate())
                .updatedDate(comment.getUpdatedDate())
                .isEdited(comment.getIsEdited())
                .build();
    }

    public Comment toComment(NewCommentDto newCommentDto) {
        return Comment.builder()
                .content(newCommentDto.getContent())
                .isEdited(false)
                .build();
    }
}