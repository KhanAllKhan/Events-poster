package ru.practicum.user;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.UserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserResponseDto;

@UtilityClass
public class UserMapper {

    public User toUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserResponseDto toUserShortDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}