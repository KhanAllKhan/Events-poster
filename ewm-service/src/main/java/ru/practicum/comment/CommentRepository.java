package ru.practicum.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByEventId(Long eventId, Pageable pageable);

    List<Comment> findByAuthorId(Long authorId);

    List<Comment> findByEventIdAndAuthorId(Long eventId, Long authorId);

    Optional<Comment> findByIdAndAuthorId(Long commentId, Long authorId);

    @Query("SELECT c FROM Comment c WHERE c.event.id = :eventId ORDER BY c.createdDate DESC")
    List<Comment> findLatestCommentsByEventId(@Param("eventId") Long eventId, Pageable pageable);

    boolean existsByEventIdAndAuthorId(Long eventId, Long authorId);

    @Query("SELECT c FROM Comment c WHERE LOWER(c.content) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Comment> searchByText(@Param("text") String text, Pageable pageable);

    long countByEventId(Long eventId);

    List<Comment> findAllByIdIn(List<Long> ids);

    @Query("SELECT c.event.id, COUNT(c) FROM Comment c WHERE c.event.id IN :eventIds GROUP BY c.event.id")
    List<Object[]> countByEventIdIn(@Param("eventIds") List<Long> eventIds);
}