package SD.User.service.implementations;

import SD.User.entity.Role;
import SD.User.entity.UserEntity;
import SD.User.models.UserDto;
import SD.User.models.UserRequestDto;
import SD.User.repository.UserRepository;
import SD.User.service.UserService;
import SD.User.service.exceptions.UserNotFoundException;
import SD.User.service.mappers.UserRequestDtoToUserMapper;
import SD.User.service.mappers.UserToUserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {
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
    public Optional<UserDto> create(UserRequestDto userRequestDto) {
        UserEntity user = UserRequestDtoToUserMapper.convert(userRequestDto);
        UserEntity savedUser = userRepository.save(user);
        UserDto userDto = UserToUserDtoMapper.convert(savedUser);
        return Optional.of(userDto);
    }

    @Override
    public Optional<UserDto> update(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            UserEntity existingUser = optionalUser.get();
            existingUser.setUsername(userDto.getUsername());
            existingUser.setEmail(userDto.getEmail());
            userRepository.save(existingUser);
            return Optional.of(UserToUserDtoMapper.convert(existingUser));
        } else {
            throw new UserNotFoundException("User with ID " + userDto.getId() + " not found.");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
