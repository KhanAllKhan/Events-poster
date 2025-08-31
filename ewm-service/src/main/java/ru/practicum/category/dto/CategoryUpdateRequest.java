package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 50, message = "Category name must not exceed 50 characters")
    private String name;
}