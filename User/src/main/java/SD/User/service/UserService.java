package SD.User.service;

import SD.User.models.UserDto;
import SD.User.models.UserRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component()
public interface UserService {
    Optional<List<UserDto>> findAll();
    Optional<UserDto> create(UserRequestDto userRequestDto);
    Optional<UserDto> update(UserDto userDto);
    void delete(Long id);
}
