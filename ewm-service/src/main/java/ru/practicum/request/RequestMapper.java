package ru.practicum.request;

import lombok.experimental.UtilityClass;
import ru.practicum.request.dto.RequestDto;

@UtilityClass
public class RequestMapper {
    public RequestDto toParticipationRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .created(request.getCreated())
                .requester(request.getId())
                .status(request.getStatus())
                .build();
    }

    public Request toRequest(RequestDto participationRequestDto) {
        return Request.builder()
                .id(participationRequestDto.getId())
                .event(null)
                .created(participationRequestDto.getCreated())
                .requester(null)
                .status(participationRequestDto.getStatus())
                .build();
    }
}