package SD.User.service.mappers;

import SD.User.entity.UserEntity;
import SD.User.models.UserDto;

public class UserToUserDtoMapper {
    public static UserDto convert(UserEntity user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
