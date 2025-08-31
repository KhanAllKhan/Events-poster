package ru.practicum.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompilationDto {
    private Long id;
    private Set<Long> events;
    private Boolean pinned;
    @Size(max = 50)
    private String title;
}