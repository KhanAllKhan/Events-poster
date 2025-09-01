package ru.practicum.comment;

import lombok.*;
import ru.practicum.event.Event;
import ru.practicum.user.User;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_edited", nullable = false)
    private Boolean isEdited;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        isEdited = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
        isEdited = true;
    }
}