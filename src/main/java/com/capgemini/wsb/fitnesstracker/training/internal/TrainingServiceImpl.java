package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    /**
     * Retrieve list of all trainings
     * @return List of Training objects
     */
    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Retrieve trainings by user primary key
     * @param id user primary key
     * @return List of Training objects
     */
    public List<Training> getAllTrainingsByUser(Long id) {
        return trainingRepository.findByUserId(id);
    }

    /**
     * Retrieve trainings finished after given date
     * @param date date
     * @return List of Training objects
     */
    public List<Training> getAllTrainingsByFinishedDate(Date date){
        return trainingRepository.findTrainingsFinishedAfterGivenDate(date);
    }

}
