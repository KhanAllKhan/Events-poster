package ru.practicum.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ViewsStatsRequest {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private Boolean unique;
    private Integer limit;
    private String application;

    public boolean hasLimitCondition() {
        return limit != null && limit > 0;
    }


    public Boolean isUnique() {
        return unique;
    }
}