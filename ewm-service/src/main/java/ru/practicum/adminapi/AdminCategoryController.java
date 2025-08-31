package ru.practicum.adminapi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.CategoryService;
import ru.practicum.category.dto.NewCategoryDto;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("POST запрос на создание новой категории: {}", newCategoryDto);
        return categoryService.addNewCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.info("DELETE запрос на удалении категории с id =  {} ", catId);
        categoryService.deleteCategoryById(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable(value = "catId") @Min(1) Long catId,
                                      @RequestBody @Valid CategoryDto categoryDto) {
        log.info("PATCH запрос на обновдение категории с id = {}", catId);
        return categoryService.updateCategory(catId, categoryDto);
    }
}