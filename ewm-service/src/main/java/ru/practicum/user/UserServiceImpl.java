package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.UserRequest;
import ru.practicum.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto addNewUser(UserRequest userRequest) {
        User user = userRepository.save(UserMapper.toUser(userRequest));
        return UserMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id= " + userId + " не найден");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getListUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from / size, size);
        return (ids != null) ? userRepository.findByIdIn(ids, page)
                .stream().map(UserMapper::toUserDto).collect(Collectors.toList()) : userRepository.findAll(page)
                .stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}