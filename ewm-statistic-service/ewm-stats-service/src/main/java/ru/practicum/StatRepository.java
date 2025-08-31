package ru.practicum;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.dto.ViewsStatsRequest;

import java.util.List;

public interface StatRepository {
    void saveHit(EndpointHit hit);

    List<ViewStats> getStats(ViewsStatsRequest request);

    List<ViewStats> getUniqueStats(ViewsStatsRequest request);
}