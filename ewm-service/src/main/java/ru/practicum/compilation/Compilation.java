package ru.practicum.compilation;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.event.Event;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(name = "compilations_to_event", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
    @Column(name = "pinned")
    private Boolean pinned;
    @Column(name = "title", nullable = false, length = 50)
    private String title;
}