package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserBasicDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserBasicEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping("")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/simple")
    public List<UserBasicDto> getAllUsersBasicData() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if(optionalUser.isPresent()){
            return userMapper.toDto(optionalUser.get());
        }
        else {
            return new UserDto(null, "", "", null, "");
        }

    }

    @GetMapping("/email")
    public List<UserBasicEmailDto> findUserByEmail(@RequestParam String email) {
        System.out.println("Received email: " + email);
        Optional<User> optionalUser = userService.findUserByEmail(email);
        List<UserBasicEmailDto> userList = new ArrayList<>();
        if (optionalUser.isPresent()) {
            userList.add(userMapper.toBasicEmailDto(optionalUser.get()));
        } else {
            userList.add(new UserBasicEmailDto(null, ""));
        }
        return userList;
    }

    @GetMapping("/older/{birthDate}")
    public List<User> getOlderUsers(@PathVariable String birthDate){
        LocalDate date = LocalDate.parse(birthDate);
        return userService.getUserOlderThenDate(date);
    }

    @PostMapping("")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User userCreated = userMapper.toEntity(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/v1/updateuser")
    public User updateUser(@RequestBody User updatedUser) {
        //User user = userMapper.toEntity(updatedUserDto);
        return userService.updateUser(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User with id: " + userId + " deleted with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}