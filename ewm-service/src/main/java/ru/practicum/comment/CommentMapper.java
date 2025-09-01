package ru.practicum.comment;

import lombok.experimental.UtilityClass;
import ru.practicum.comment.dto.CommentDto;

@UtilityClass
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getContent())
                .authorId(comment.getAuthor().getId())
                .eventId(comment.getEvent().getId())
                .created(comment.getCreatedDate())
                .updated(comment.getUpdatedDate())
                .isEdited(comment.getIsEdited())
                .build();
    }

    public Comment toComment(ru.practicum.comment.dto.NewCommentDto newCommentDto) {
        return Comment.builder()
                .content(newCommentDto.getText())
                .isEdited(false)
                .build();
    }
}