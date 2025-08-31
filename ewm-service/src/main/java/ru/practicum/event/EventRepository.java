package ru.practicum.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.category.Category;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Optional<Event> findByInitiatorIdAndId(Long userId, Long eventId);

    List<Event> findByCategory(Category category);

    List<Event> findAllByIdIn(List<Long> ids);
}