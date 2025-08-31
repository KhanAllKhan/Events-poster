package ru.practicum.location;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "location")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "id")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lat")
    private Float lat;
    @Column(name = "lon")
    private Float lon;
}