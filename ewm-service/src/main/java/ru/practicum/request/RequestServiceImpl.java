package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.Event;
import ru.practicum.event.EventRepository;
import ru.practicum.event.dto.EventStatus;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ParametersException;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public RequestDto addNewRequest(Long userId, Long eventId) {
        User user = checkUser(userId);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id= " + eventId + " не найдено"));
        LocalDateTime createdOn = LocalDateTime.now();
        validateNewRequest(event, userId, eventId);
        Request request = new Request();
        request.setCreated(createdOn);
        request.setRequester(user);
        request.setEvent(event);

        if (event.isRequestModeration()) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
        }

        requestRepository.save(request);

        if (event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        }

        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public List<RequestDto> getRequestsByUserId(Long userId) {
        checkUser(userId);
        List<Request> result = requestRepository.findAllByRequesterId(userId);
        return result.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        checkUser(userId);
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(
                () -> new NotFoundException("Запрос с id= " + requestId + " не найден"));
        if (request.getStatus().equals(RequestStatus.CANCELED) || request.getStatus().equals(RequestStatus.REJECTED)) {
            throw new ParametersException("Запрос не подтвержден");
        }
        request.setStatus(RequestStatus.CANCELED);
        Request requestAfterSave = requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(requestAfterSave);
    }

    private User checkUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Категории с id = " + userId + " не существует"));
    }

    private void validateNewRequest(Event event, Long userId, Long eventId) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Пользователь с id= " + userId + " не инициатор события");
        }
        if (event.getParticipantLimit() > 0 && event.getParticipantLimit() <= requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED)) {
            throw new ConflictException("Превышен лимит участников события");
        }
        if (!event.getEventStatus().equals(EventStatus.PUBLISHED)) {
            throw new ConflictException("Событие не опубликовано");
        }
        if (requestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new ConflictException("Попытка добаления дубликата");
        }
    }
}