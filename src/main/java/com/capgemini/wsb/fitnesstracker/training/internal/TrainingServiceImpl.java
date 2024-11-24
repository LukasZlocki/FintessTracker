package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
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
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;


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

    @Override
    public TrainingDto createTraining(TrainingInputDto trainingInputDto) throws UserNotFoundException {
        log.info("Creating Training {}", trainingInputDto);

        var user = userProvider.getUser(trainingInputDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(trainingInputDto.getUserId()));

        TrainingDto trainingDto = trainingMapper.inputDtoToTrainingDto(user, trainingInputDto);

        Training training = trainingMapper.toEntity(trainingDto);

        return trainingMapper.toTrainingDto(trainingRepository.save(training));
    }

    @Override
    public TrainingDto updateTraining(Long id, TrainingInputDto trainingInputDto) throws UserNotFoundException, TrainingNotFoundException {
        var training = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));

        var user = userProvider.getUser(trainingInputDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(id));

        TrainingDto trainingDto = trainingMapper.inputDtoToTrainingDto(user, trainingInputDto);

        // Create updated dto and update training based on it
        TrainingDto oldTrainingDto = trainingMapper.toTrainingDto(training);
        TrainingDto newTrainingDto = oldTrainingDto.updateTraining(trainingDto).addId(id);
        Training newTraining = trainingMapper.toEntity(newTrainingDto);
        newTraining.setId(id);

        log.info("Updating Training {}", newTraining);
        trainingRepository.save(newTraining);
        return newTrainingDto;
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

    /**
     * Retrieve list of Trainings by given activity type
     * @param activityType activity type to filter trainings
     * @return list of trainings as TrainingDto
     */
    public List<TrainingDto> getTrainingsOfActivityType(String activityType) {
        return trainingRepository.findAll()
                .stream()
                .filter(s -> activityType.equalsIgnoreCase(s.getActivityType().toString()))
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

}
