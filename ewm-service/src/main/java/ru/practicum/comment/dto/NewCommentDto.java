package ru.practicum.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCommentDto {
    @NotBlank(message = "Comment content cannot be blank")
    @Size(min = 1, max = 2000, message = "Comment must be between 1 and 2000 characters")
    private String content;
}