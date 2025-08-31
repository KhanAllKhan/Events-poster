package ru.practicum.user;

import ru.practicum.user.dto.UserRequest;
import ru.practicum.user.dto.UserDto;
import java.util.List;


public interface UserService {
    UserDto addNewUser(UserRequest userRequest);

    void deleteUser(Long userId);

    List<UserDto> getListUsers(List<Long> ids, Integer from, Integer size);
}