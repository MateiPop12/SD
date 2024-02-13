package SD.User.service.implementations;

import SD.User.entity.User;
import SD.User.models.UserDto;
import SD.User.models.UserRequestDto;
import SD.User.repository.UserRepository;
import SD.User.service.UserService;
import SD.User.service.mappers.UserRequestDtoToUserMapper;
import SD.User.service.mappers.UserToUserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<List<UserDto>> findAll() {
        return Optional.of(userRepository.findAll().stream().map(UserToUserDtoMapper::convert).collect(Collectors.toList()));
    }

    @Override
    public Optional<UserDto> fnindByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> create(UserRequestDto userRequestDto) {
        User user = UserRequestDtoToUserMapper.convert(userRequestDto);

        User savedUser = userRepository.save(user);

        UserDto userDto = UserToUserDtoMapper.convert(savedUser);
        return Optional.of(userDto);
    }

    @Override
    public Optional<UserDto> update(User user) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
