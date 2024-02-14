package SD.User.service.mappers;

import SD.User.entity.UserEntity;
import SD.User.models.UserRequestDto;

public class UserRequestDtoToUserMapper {
    public static UserEntity convert(UserRequestDto userRequestDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        return user;
    }
}
