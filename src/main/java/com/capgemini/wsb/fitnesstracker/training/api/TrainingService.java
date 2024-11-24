package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.util.List;

public interface TrainingService {

    /**
     * Get all trainings
     *
     * @return List of trainings object class
     */
    public List<Training> getAllTrainings();

    /**
     * Create new training
     * Throws a {@link UserNotFoundException} when trying to create training for non-existent user
     *
     * @param trainingInputDto Dto with user id data to be mapped onto training entity and saved to db
     * @return {@link TrainingDto} of created training
     */
    TrainingDto createTraining(TrainingInputDto trainingInputDto) throws UserNotFoundException;

    /**
     * Updates training of specified {@param id} with the data in {@param trainingInputDto}
     * Throws a {@link UserNotFoundException} when trying to update training for non-existent user
     * Throws a {@link TrainingNotFoundException} when trying to update non-existent training
     *
     * @param id id of training to be updated
     * @param trainingInputDto data used to replace current training with
     * @return {@link TrainingDto} of updated training
     */
    TrainingDto updateTraining(Long id, TrainingInputDto trainingInputDto) throws UserNotFoundException, TrainingNotFoundException;
}
