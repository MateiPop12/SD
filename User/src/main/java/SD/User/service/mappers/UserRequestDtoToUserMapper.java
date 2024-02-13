package SD.User.service.mappers;

import SD.User.entity.User;
import SD.User.models.UserRequestDto;

public class UserRequestDtoToUserMapper {
    public static User convert(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        return user;
    }
}
