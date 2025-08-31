package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.location.LocationDto;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEventDto {
    @NotBlank(message = "Аннотация не может быть пустой")
    @Length(max = 2000, min = 20, message = "Аннотация должна быть от 20 до 2000 символов")
    private String annotation;

    @NotNull(message = "Категория обязательна")
    @Positive(message = "Категория должна быть положительным числом")
    private Long category;

    @NotBlank(message = "Описание не может быть пустым")
    @Length(max = 7000, min = 20, message = "Описание должно быть от 20 до 7000 символов")
    private String description;

    @NotNull(message = "Дата события обязательна")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "Локация обязательна")
    @Valid
    private LocationDto location;

    private boolean paid;

    @PositiveOrZero(message = "Лимит участников не может быть отрицательным")
    private int participantLimit;

    private boolean requestModeration = true;

    @NotNull(message = "Заголовок обязательный")
    @Length(min = 3, max = 120, message = "Заголовок должен быть от 3 до 120 символов")
    private String title;
}