package ru.practicum.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}