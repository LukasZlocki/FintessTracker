package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserBasicDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserBasicEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/v1/getolderuser/{birthDate}")
    public List<User> getOlderUsers(@PathVariable String birthDate){
        LocalDate date = LocalDate.parse(birthDate);
        return userService.getUserOlderThenDate(date);
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

    @PostMapping("/v1/updateuser")
    public User updateUser(@RequestBody User updatedUser) {
        //User user = userMapper.toEntity(updatedUserDto);
        return userService.updateUser(updatedUser);
    }

    @DeleteMapping("/v1/deleteuser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}