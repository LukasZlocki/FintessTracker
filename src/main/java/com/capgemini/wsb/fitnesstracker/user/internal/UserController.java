package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping("/v1/users")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/v1/usersbasic")
    public List<UserBasicDto> getAllUsersBasicData() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

    @GetMapping("/v1/finduserbyemail/{email}")
    public UserBasicEmailDto findUserByEmail(@PathVariable String email) {
        System.out.println("Received email: " + email);
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if(optionalUser.isPresent()) {
            return userMapper.toBasicEmailDto(optionalUser.get());
        } else {
            // ToDo : handle user not found scenario
            return new UserBasicEmailDto(null, "");
        }
    }

    @GetMapping("/v1/getolderuser/{birth}")
    public User getOlderUsers(@PathVariable birthDate){
        return userService.getUserOlderThenDate(birthDate);
    }

    @PostMapping("/v1/adduser")
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);

        // Demonstracja how to use @RequestBody
        //System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        // TODO: saveUser with Service and return User
        //return null;
    }

    @DeleteMapping("/v1/deleteuser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}