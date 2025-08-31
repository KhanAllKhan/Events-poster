package ru.practicum;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.dto.ViewsStatsRequest;

import java.util.List;

public interface StatService {
    void saveHit(EndpointHit hit);

    List<ViewStats> getViewStatsList(ViewsStatsRequest request);
}