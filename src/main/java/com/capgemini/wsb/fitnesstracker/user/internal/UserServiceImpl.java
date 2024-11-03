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

    /**
     * Update user data by given primary key and object
     * @param userId user primary key
     * @param updatedUser user data to update
     * @return updated user as User object
     */
    public User updateUser(Long userId, final User updatedUser) {
        Optional<User> userExisting = userRepository.findById(userId);
        User userToUpdate = userExisting.orElseThrow(() -> new RuntimeException("User not found"));

        // Update the existing user's details by setters implemented to user model
        // Ask Question :
        // if this can be done in another way by not implementing #Setters to main User model
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setBirthdate(updatedUser.getBirthdate());
        userToUpdate.setEmail(updatedUser.getEmail());

        return userRepository.save(userToUpdate);
    }

    /**
     * Delete user object
     * @param id user primary key
     */
    @Override
    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieve user  data by user's email
     * @param email the email of the user to be searched
     * @return user data as Optional User object
     */
    @Override
    public Optional<User> findUserByEmail(String email) {
        String _emailLowerCase = email.toLowerCase();
        return userRepository.findByEmail(_emailLowerCase);
    }

    /**
     * Query all users based on whether Their birthdate was before the date provided
     * @param birthDate date to be compare to
     * @return List of users older than give date
     */
    @Override
    public List<User> getUserOlderThenDate(LocalDate birthDate) {
        return userRepository.findUsersOlderThanDate(birthDate);
    }

    /**
     * Retrieve user by its primary key
     * @param userId primary key
     * @return Optional User object
     */
    public Optional<User> getUserById(final long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieve user by its primary key
     * @param userId id of the user to be searched
     * @return optional User object
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieve user by its email
     * @param email The email of the user to be searched
     * @return optional User object
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieve all users
     * @return List of User objects
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}