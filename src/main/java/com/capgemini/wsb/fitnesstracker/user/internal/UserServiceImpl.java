package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        return userRepository.save(user);
    }

    public User updateUser(Long userId, final User updatedUser) {
        Optional<User> userExisting = userRepository.findById(userId);
        User userToUpdate = userExisting.orElseThrow(() -> new RuntimeException("User not found"));

        // Update the existing user's details by setters implemented to user model
        // Ask Question :
        // if this can be donein another way by not implementing #Setters to main User model
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setBirthdate(updatedUser.getBirthdate());
        userToUpdate.setEmail(updatedUser.getEmail());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String _emailLowerCase = email.toLowerCase();
        return userRepository.findByEmail(_emailLowerCase);
    }

    @Override
    public List<User> getUserOlderThenDate(LocalDate birthDate) {
        return userRepository.findUsersOlderThanDate(birthDate);
    }

    public Optional<User> getUserById(final long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}