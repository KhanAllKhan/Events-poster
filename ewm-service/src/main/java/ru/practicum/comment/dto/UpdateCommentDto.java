package ru.practicum.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCommentDto {
    @Size(min = 1, max = 2000, message = "Comment must be between 1 and 2000 characters")
    private String text;
}