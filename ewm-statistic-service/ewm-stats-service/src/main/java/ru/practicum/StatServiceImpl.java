package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.dto.ViewsStatsRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public void saveHit(EndpointHit hit) {
        statRepository.saveHit(hit);
    }

    @Override
    public List<ViewStats> getViewStatsList(ViewsStatsRequest request) {
        if (request.isUnique()) {
            return statRepository.getUniqueStats(request);
        }

        return statRepository.getStats(request);
    }
}