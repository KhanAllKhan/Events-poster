package ru.practicum.category;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);

    CategoryDto addNewCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(Long catId);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);
}