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

    /**
     * Retrive all user
     * @return list of user objects
     */
    @GetMapping("")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    /**
     * Retrive all users basic data like first name, last name
     * @return list of UserBasicDto objects
     */
    @GetMapping("/simple")
    public List<UserBasicDto> getAllUsersBasicData() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

    /**
     * Retrive user by given ID
     * @param id the primary key of user to be updated
     * @return userDto object
     */
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

    /**
     * Retrive user by given email
     * @param email user email
     * @return list of UserBasicEmailDto object
     */
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

    /**
     * Retrive all users which are older then birthday date
     * @param birthDate user birth date
     * @return list of users older then given birthDAte parameter
     */
    @GetMapping("/older/{birthDate}")
    public List<User> getOlderUsers(@PathVariable String birthDate){
        LocalDate date = LocalDate.parse(birthDate);
        return userService.getUserOlderThenDate(date);
    }

    /**
     * Add user
     * @param userDto user information
     * @return status Ok and user object
     * @throws InterruptedException if operation is interrupted
     */
    @PostMapping("")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User userCreated = userService.createUser(userMapper.toEntity(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    /**
     * Update user with specific ID
     *
     * @param userId the primary key of user to be updated
     * @param updatedUserDto updated user information
     * @return updated user object
     */
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto updatedUserDto) {
        User userToUpdate = userMapper.toEntity(updatedUserDto);
        return userService.updateUser(userId, userToUpdate);
    }

    /**
     * Delete user
     * @param userId the primary key of user to be deleted
     * @return ResponseEntity with HTTP status and message
     */
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