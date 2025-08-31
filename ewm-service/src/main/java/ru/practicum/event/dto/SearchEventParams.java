package ru.practicum.event.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchEventParams {
    private String text;

    private List<String> categories;

    private Boolean paid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable = false;

    private String sort;

    @PositiveOrZero
    private Integer from = 0;

    @Positive
    private Integer size = 10;

    public List<Long> getParsedCategories() {
        if (categories == null) return null;
        return categories.stream()
                .filter(Objects::nonNull)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}