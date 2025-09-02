package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.event.Event;
import ru.practicum.event.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventsRepository;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "id"));
        return categoryRepository.findAll(pageRequest)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = checkCategory(catId);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toNewCategoryDto(newCategoryDto);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(saved);
    }

    @Override
    public void deleteCategoryById(Long catId) {
        Category category = checkCategory(catId);

        // Ищем события по идентификатору категории
        List<Event> events = eventsRepository.findByCategoryId(category.getId());
        if (!events.isEmpty()) {
            throw new ConflictException("Can't delete category due to using for some events");
        }

        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        Category existing = checkCategory(catId);
        String newName = categoryDto.getName();

        if (newName != null && !newName.equals(existing.getName())) {
            checkUniqNameCategoryIgnoreCase(newName);
        }

        existing.setName(newName);
        Category updated = categoryRepository.save(existing);
        return CategoryMapper.toCategoryDto(updated);
    }

    // Проверяет, что нет другой категории с таким же именем (игнор регистра)
    private void checkUniqNameCategoryIgnoreCase(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Категория " + name + " уже существует");
        }
    }

    // Заглушка на NotFound, если категория не найдена
    private Category checkCategory(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() ->
                        new NotFoundException("Категории с id = " + catId + " не существует"));
    }
}
