package SD.User.controller;

import SD.User.models.UserDto;
import SD.User.models.UserRequestDto;
import SD.User.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<Optional<List<UserDto>>> findAll(){
        logger.info("Requested: /user/findAll");
        Optional<List<UserDto>> userDtoList = userService.findAll();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDtoList);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        logger.info("Requested: /user/create");
        Optional<UserDto> createdUser = userService.create(userRequestDto);
        return createdUser.map(dto ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(dto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        logger.info("Requested: /user/update");
        Optional<UserDto> updatedUser = userService.update(userDto);
        return updatedUser.map(dto ->
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Requested: /user/delete");
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
