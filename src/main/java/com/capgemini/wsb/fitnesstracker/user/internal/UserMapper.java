package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserBasicDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserBasicEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    UserBasicDto toBasicDto(User user) {
        return new UserBasicDto(user.getId(),
                user.getFirstName(), user.getLastName());
    }

    UserBasicEmailDto toBasicEmailDto(User user) {
        return new UserBasicEmailDto(user.getId(),
                user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

}
